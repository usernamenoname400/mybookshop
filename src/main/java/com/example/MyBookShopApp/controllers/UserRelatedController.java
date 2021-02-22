package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.BookService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserRelatedController {
  private final BookService bookService;
  @Value("${sql.rowlimit}")
  private int rowLimit;

  public UserRelatedController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping("/cart")
  public String getCard(Model model) {
    model.addAttribute("cartBooks", bookService.getPageRecomendedBooks(0, rowLimit).getContent());
    return "cart";
  }

  @GetMapping("/postponed")
  public String getPostponed(Model model) {
    model.addAttribute("postponedBooks", bookService.getBooksPostponed());
    return "postponed";
  }
}
