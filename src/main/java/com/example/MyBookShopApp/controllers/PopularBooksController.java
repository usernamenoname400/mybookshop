package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.BookService;
import com.example.MyBookShopApp.data.BooksPageDTO;
import com.example.MyBookShopApp.helpers.ThymLeafStringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

  @GetMapping("/popular/{bookId:\\d+}")
  public String getRecent(@PathVariable Integer bookId, Model model) {
    model.addAttribute("book", bookService.getBookData(bookId));
    model.addAttribute("stringHelper", new ThymLeafStringHelper());
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
