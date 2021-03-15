package com.example.MyBookShopApp.controllers.book;

import com.example.MyBookShopApp.data.dto.Book;
import com.example.MyBookShopApp.data.dto.BookReview;
import com.example.MyBookShopApp.data.dto.Rating;
import com.example.MyBookShopApp.data.service.BookReviewService;
import com.example.MyBookShopApp.data.service.BookService;
import com.example.MyBookShopApp.data.service.RatingService;
import com.example.MyBookShopApp.data.service.ResourceStorage;
import com.example.MyBookShopApp.helpers.ThymLeafStringHelper;
import com.example.MyBookShopApp.security.BookStoreUserRegister;
import com.example.MyBookShopApp.security.BookstoreUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
import java.util.logging.Logger;

@Controller
public class BooksController {
  private final BookService bookService;
  private final BookReviewService bookReviewService;
  private final RatingService ratingService;
  private final ResourceStorage storage;
  private final BookStoreUserRegister bookStoreUserRegister;

  @Autowired
  public BooksController(
      BookService bookService,
      ResourceStorage storage,
      BookReviewService bookReviewService,
      RatingService ratingService,
      BookStoreUserRegister bookStoreUserRegister
  ) {
    this.bookService = bookService;
    this.storage = storage;
    this.bookReviewService = bookReviewService;
    this.ratingService = ratingService;
    this.bookStoreUserRegister = bookStoreUserRegister;
  }

  @GetMapping("/books/{slug}")
  public String getBookBySlug(
      @CookieValue(value = "postponedContents", required = false) String postponedContents,
      @CookieValue(name = "cartContents", required = false) String cartContents,
      @PathVariable("slug") String slug,
      Model model
  ) {
    Book book = bookService.getBookBySlug(slug);
    if (book == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find book");
    }
    model.addAttribute("book", book);
    model.addAttribute("stringHelper", new ThymLeafStringHelper());
    model.addAttribute("isPostponed", (postponedContents != null && postponedContents.contains(slug)));
    model.addAttribute("isInCart", (cartContents != null && cartContents.contains(slug)));
    model.addAttribute("curUsr", bookStoreUserRegister.getCurrentUser());
    return "books/slug";
  }

  @PostMapping("/books/{slug}/img/save")
  public String saveNewBookImage(@RequestParam("file") MultipartFile file, @PathVariable("slug") String slug) throws IOException {
    String savePath = storage.saveNewBookImage(file, slug);
    Book bookToUpdate = bookService.getBookBySlug(slug);
    bookToUpdate.setImage(savePath);
    bookService.SaveBook(bookToUpdate);

    return "redirect:/books/" + slug;
  }

  @PostMapping("/books/{slug}/addReview")
  public String addReview(
      @RequestParam("reviewText") String reviewText,
      @PathVariable("slug") String slug,
      @CookieValue(value = "JSESSIONID", required = true) String sessionId
  ) {
    String reviewAuthor = null;
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal != null && principal instanceof BookstoreUserDetails) {
      reviewAuthor = ((BookstoreUserDetails)principal).getBookstoreUser().getName();
    } else if (principal != null && principal instanceof DefaultOAuth2User) {
      reviewAuthor = (String)((DefaultOAuth2User)principal).getAttributes().get("name");
    } else {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized");
    }

    Book book = bookService.getBookBySlug(slug);
    if (book == null) {
      Logger.getLogger(this.getClass().getSimpleName()).warning("Book `" + slug + "` not found");
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find book");
    }
    BookReview review = new BookReview();

    review.setBook(book);
    review.setSessionId(sessionId);
    review.setUserName(reviewAuthor);
    review.setText(reviewText);
    review.setTime(new Date());
    review.setUserId(0);
    bookReviewService.saveReview(review);

    return "redirect:/books/" + slug;
  }

  @PostMapping("/books/{slug}/rating")
  public String addRating(
      @RequestParam("value") Integer value,
      @PathVariable("slug") String slug
  ) {
    Rating rating = ratingService.getBookRatingBySlug(slug);
    if (rating == null) {
      Logger.getLogger(this.getClass().getSimpleName()).warning("Book `" + slug + "` not found");
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find book");
    }
    switch (value) {
      case 1:
        rating.setRating1(rating.getRating1() + 1);
        break;
      case 2:
        rating.setRating2(rating.getRating2() + 1);
        break;
      case 3:
        rating.setRating3(rating.getRating3() + 1);
        break;
      case 4:
        rating.setRating4(rating.getRating4() + 1);
        break;
      case 5:
        rating.setRating5(rating.getRating5() + 1);
        break;
      default:
        Logger.getLogger(this.getClass().getSimpleName()).warning("Book `" + slug + "` not found");
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rating must be 1..5");
    }

    ratingService.saveRating(rating);

    return "redirect:/books/" + slug;
  }

  @GetMapping("/books/download/{hash}")
  public ResponseEntity<ByteArrayResource> bookFile(@PathVariable("hash") String hash) throws IOException {
    Path path = storage.getBookFilePath(hash);
    Logger.getLogger(this.getClass().getName()).info("Download path = " + path);

    MediaType mediaType = storage.getBookFileType(hash);
    Logger.getLogger(this.getClass().getName()).info("Media type = " + mediaType);

    byte[] data = storage.getBookFileByteArray(hash);
    Logger.getLogger(this.getClass().getName()).info("File data length = " + data.length);

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
        .contentType(mediaType)
        .contentLength(data.length)
        .body(new ByteArrayResource(data));
  }
}
