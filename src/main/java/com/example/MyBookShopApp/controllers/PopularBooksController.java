package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookService;
import com.example.MyBookShopApp.data.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PopularBooksController {
  private final BookService bookService;
  private final RatingService ratingService;
  @Value("${sql.rowlimit}")
  private int rowLimit;

  @Autowired
  public PopularBooksController(BookService bookService, RatingService ratingService) {
    this.bookService = bookService;
    this.ratingService = ratingService;
  }

  @ModelAttribute("popularBooks")
  public List<Book> popularBooks() {
    return bookService.getBooksData(rowLimit);
  }

  @GetMapping("/popular")
  public String getPopular() {
    return "books/popular";
  }

  @GetMapping("/popular/{bookId:\\d+}")
  public String getRecent(@PathVariable Integer bookId, Model model) {
    model.addAttribute("book", bookService.getBookData(bookId));
    model.addAttribute("ratings", ratingService.getBookRating(bookId));
    return "books/slug";
  }
}
