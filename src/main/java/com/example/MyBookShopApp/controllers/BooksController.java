package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.BookService;
import com.example.MyBookShopApp.helpers.ThymLeafStringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BooksController {
  private final BookService bookService;

  @Autowired
  public BooksController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping("/books/{bookId:\\d+}")
  public String getRecent(@PathVariable Integer bookId, Model model) {
    model.addAttribute("book", bookService.getBookData(bookId));
    model.addAttribute("stringHelper", new ThymLeafStringHelper());
    return "books/slug";
  }
}
