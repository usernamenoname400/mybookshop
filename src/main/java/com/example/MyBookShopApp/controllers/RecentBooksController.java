package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.BookService;
import com.example.MyBookShopApp.data.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Calendar;
import java.util.Date;

@Controller
public class RecentBooksController {
  private final BookService bookService;
  private final RatingService ratingService;
  @Value("${sql.rowlimit}")
  private int rowLimit;

  @Autowired
  public RecentBooksController(BookService bookService, RatingService ratingService) {
    this.bookService = bookService;
    this.ratingService = ratingService;
  }

  @GetMapping("/recent")
  public String getRecent(Model model) {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.MONTH, -1);
    model.addAttribute("recentBooks", bookService.getRecentBooksData(cal.getTime(), new Date(), rowLimit));
    model.addAttribute("dateFrom", cal.getTime());
    model.addAttribute("dateTo", new Date());
    return "books/recent";
  }

  @PostMapping("/recent")
  public String postRecent(Date dateFrom, Date dateTo, Model model) {
    model.addAttribute("recentBooks", bookService.getRecentBooksData(dateFrom, dateTo, rowLimit));
    model.addAttribute("dateFrom", dateFrom);
    model.addAttribute("dateTo", dateTo);
    return "books/recent";
  }

  @GetMapping("/recent/{bookId:\\d+}")
  public String getRecent(@PathVariable Integer bookId, Model model) {
    model.addAttribute("book", bookService.getBookData(bookId));
    model.addAttribute("ratings", ratingService.getBookRating(bookId));
    return "books/slug";
  }
}
