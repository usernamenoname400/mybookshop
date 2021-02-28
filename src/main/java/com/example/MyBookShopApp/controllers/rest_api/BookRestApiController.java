package com.example.MyBookShopApp.controllers.rest_api;

import com.example.MyBookShopApp.data.wrapper.ApiResponse;
import com.example.MyBookShopApp.data.dto.Book;
import com.example.MyBookShopApp.data.service.BookService;
import com.example.MyBookShopApp.errors.BookstoreApiWrongParameterException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api(description = "book data api")
public class BookRestApiController {
  private final BookService bookService;

  @Autowired
  public BookRestApiController(BookService bookService) {
    this.bookService = bookService;
  }

  private void SuccessBoiler(ApiResponse<Book> response, List<Book> books) {
    for (final Book book: books) {
      Link link = linkTo(methodOn(BookRestApiController.class).bookBySlug(book.getSlug())).withSelfRel();
      book.add(link);
    }
    response.setDebugMessage("successful request");
    response.setMessage("data size: " + books.size() + " elements");
    response.setStatus(HttpStatus.OK);
    response.setTimeStamp(LocalDateTime.now());
    response.setData(books);
  }

  @GetMapping("/books/by-slug")
  @ApiOperation("Get book by mnemonical identity")
  public ResponseEntity<ApiResponse<Book>> bookBySlug(@RequestParam("slug") String slug) {
    ApiResponse<Book> response = new ApiResponse<>();
    List<Book> data = null;
    Book book = bookService.getBookBySlug(slug);
    if (book != null) {
      Link link = linkTo(methodOn(BookRestApiController.class).bookBySlug(slug)).withSelfRel();
      book.add(link);
      data = new ArrayList<>();
      data.add(book);
      response.setMessage("data size: 1 elements");
    } else {
      response.setMessage("data size: 0 elements");
    }

    response.setDebugMessage("successful request");
    response.setStatus(HttpStatus.OK);
    response.setTimeStamp(LocalDateTime.now());
    response.setData(data);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/books/by-author")
  @ApiOperation("Get book list of bookshop filtered by author name")
  public ResponseEntity<ApiResponse<Book>> booksByAuthor(@RequestParam("author") String authorName) {
    ApiResponse<Book> response = new ApiResponse<>();
    List<Book> data = bookService.getBooksByAuthor(authorName);
    SuccessBoiler(response, data);
    Link link = linkTo(methodOn(BookRestApiController.class).booksByAuthor(authorName)).withSelfRel();
    response.add(link);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/books/by-title")
  @ApiOperation("Get book list of bookshop filtered by book title")
  public ResponseEntity<ApiResponse<Book>> booksByTitle(@RequestParam("title") String bookTitle)
      throws BookstoreApiWrongParameterException
  {
    ApiResponse<Book> response = new ApiResponse<>();
    List<Book> data = bookService.getBooksByTitle(bookTitle);
    SuccessBoiler(response, data);
    Link link = linkTo(methodOn(BookRestApiController.class).booksByTitle(bookTitle)).withSelfRel();
    response.add(link);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/books/by-price-range")
  @ApiOperation("Get book list of bookshop filtered by regular price range")
  public ResponseEntity<ApiResponse<Book>> booksByPriceRange(@RequestParam("min") Integer min, @RequestParam("max") Integer max) {
    ApiResponse<Book> response = new ApiResponse<>();
    List<Book> data = bookService.getBooksWithPriceBetween(min, max);
    SuccessBoiler(response, data);
    Link link = linkTo(methodOn(BookRestApiController.class).booksByPriceRange(min, max)).withSelfRel();
    response.add(link);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/books/with-max-discount")
  @ApiOperation("Get book list of bookshop with maximum discount")
  public ResponseEntity<ApiResponse<Book>> booksWithMaxPrice() {
    ApiResponse<Book> response = new ApiResponse<>();
    List<Book> data = bookService.getBooksWithMaxDiscount();
    SuccessBoiler(response, data);
    Link link = linkTo(methodOn(BookRestApiController.class).booksWithMaxPrice()).withSelfRel();
    response.add(link);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/books/bestsellers")
  @ApiOperation("Get book list of bookshop filtered bestsellers only")
  public ResponseEntity<ApiResponse<Book>> booksBestsellers() {
    ApiResponse<Book> response = new ApiResponse<>();
    List<Book> data = bookService.getBestSellers();
    SuccessBoiler(response, data);
    Link link = linkTo(methodOn(BookRestApiController.class).booksBestsellers()).withSelfRel();
    response.add(link);
    return ResponseEntity.ok(response);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ApiResponse<Book>> handleMissingServletRequestParameterException(Exception exception) {
    ApiResponse<Book> response = new ApiResponse<>(HttpStatus.BAD_REQUEST, "Missing required parameters", exception);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BookstoreApiWrongParameterException.class)
  public ResponseEntity<ApiResponse<Book>> handleBookstoreApiWrongParameterException(Exception exception) {
    ApiResponse<Book> response = new ApiResponse<>(HttpStatus.BAD_REQUEST, "Bad parameter value", exception);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }
}
