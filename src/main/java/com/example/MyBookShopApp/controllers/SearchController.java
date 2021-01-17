package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {
  private final BookService bookService;

  @Autowired
  public SearchController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping("/search")
  public String mainPage(Model model) {
    model.addAttribute("search", "");
    return "search/index";
  }

  @PostMapping("/search")
  public String mainPage(@RequestParam @DefaultValue("") String queryPage, Model model) {
    if (!queryPage.equals("")) {
      model.addAttribute("booksSearch", bookService.getBooksData(-1));
    }
    model.addAttribute("search", queryPage);
    return "search/index";
  }
}
