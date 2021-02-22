package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.BookService;
import com.example.MyBookShopApp.data.BooksPageDTO;
import com.example.MyBookShopApp.helpers.ThymLeafStringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Controller
public class RecentBooksController {
  private final BookService bookService;
  private final Date oneMontBefore;
  private final Date thisDate;

  @Value("${sql.rowlimit}")
  private int rowLimit;

  @Autowired
  public RecentBooksController(BookService bookService) {
    this.bookService = bookService;
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.MONTH, -1);
    oneMontBefore = calendar.getTime();
    thisDate = new Date();
  }

  @GetMapping("/recent")
  public String getRecent(Model model) {
    model.addAttribute("recentBooks", bookService.getPageOfRecent(oneMontBefore, thisDate, 0, rowLimit));
    model.addAttribute("dateFrom", oneMontBefore.getTime());
    model.addAttribute("dateTo", new Date());
    return "books/recent";
  }

  @GetMapping("/recent/{bookId:\\d+}")
  public String getRecent(@PathVariable Integer bookId, Model model) {
    model.addAttribute("book", bookService.getBookData(bookId));
    model.addAttribute("stringHelper", new ThymLeafStringHelper());
    return "books/slug";
  }

  @GetMapping("/recent/page")
  @ResponseBody
  public BooksPageDTO getNextSearchPage(
      @RequestParam("offset") Integer offset,
      @RequestParam("limit") Integer limit,
      @RequestParam(value = "from", defaultValue = "") String from,
      @RequestParam(value = "to", defaultValue = "") String to
  ) {
    Date fromDt = new Date();
    Date toDt = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
    try {
      fromDt = formatter.parse(from);
    } catch (ParseException e) {
      fromDt = oneMontBefore;
    }
    try {
      toDt = formatter.parse(to);
    } catch (ParseException e) {
      toDt = thisDate;
    }
    return new BooksPageDTO(bookService.getPageOfRecent(fromDt, toDt, offset, limit).getContent());
  }
}
