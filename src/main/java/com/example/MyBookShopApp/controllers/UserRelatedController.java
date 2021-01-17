package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserRelatedController {
  private final BookService bookService;

  public UserRelatedController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping("/cart")
  public String getCard(Model model) {
    model.addAttribute("cartBooks", bookService.getBooksCart());
    return "cart";
  }

  @GetMapping("/postponed")
  public String getPostponed(Model model) {
    model.addAttribute("postponedBooks", bookService.getBooksPostponed());
    return "postponed";
  }
}
