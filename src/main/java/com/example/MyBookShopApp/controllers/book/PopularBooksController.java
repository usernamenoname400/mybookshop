package com.example.MyBookShopApp.controllers.book;

import com.example.MyBookShopApp.data.service.BookService;
import com.example.MyBookShopApp.data.dto.BooksPageDTO;
import com.example.MyBookShopApp.helpers.ThymLeafStringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
public class PopularBooksController {
  private final BookService bookService;
  @Value("${sql.rowlimit}")
  private int rowLimit;

  @Autowired
  public PopularBooksController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping("/popular")
  public String getPopular(Model model) {
    model.addAttribute("popularBooks", bookService.getPageOfPopular(0, rowLimit).getContent());
    return "books/popular";
  }

  @GetMapping("/popular/{slug}")
  public String getRecent(
      @CookieValue(value = "postponedContents", required = false) String postponedContents,
      @CookieValue(name = "cartContents", required = false) String cartContents,
      @PathVariable("slug") String slug,
      Model model
  ) {
    Logger.getLogger(this.getClass().getSimpleName()).info("slug = " + slug);
    Logger.getLogger(this.getClass().getSimpleName()).info("postponedContents = " + postponedContents);
    Logger.getLogger(this.getClass().getSimpleName()).info("cartContents = " + cartContents);
    model.addAttribute("book", bookService.getBookBySlug(slug));
    model.addAttribute("stringHelper", new ThymLeafStringHelper());
    model.addAttribute("isPostponed", (postponedContents != null && postponedContents.contains(slug)));
    model.addAttribute("isInCart", (cartContents != null && cartContents.contains(slug)));
    return "books/slug";
  }

  @GetMapping("/popular/page")
  @ResponseBody
  public BooksPageDTO getNextSearchPage(
      @RequestParam("offset") Integer offset,
      @RequestParam("limit") Integer limit
  ) {
    return new BooksPageDTO(bookService.getPageOfPopular(offset, limit).getContent());
  }
}
