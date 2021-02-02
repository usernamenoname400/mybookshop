package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookService;
import com.example.MyBookShopApp.data.TagService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class MainPageController {
  private final BookService bookService;
  private final TagService tagService;
  @Value("${sql.rowlimit}")
  private int rowLimit;

  @Autowired
  public MainPageController(BookService bookService, TagService tagService) {
    this.bookService = bookService;
    this.tagService = tagService;
  }

  @ModelAttribute("recommendedBooks")
  public List<Book> recommendedBooks() {
    return bookService.getBooksData(rowLimit);
  }

  @ModelAttribute("recentBooks")
  public List<Book> recentBooks() {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.MONTH, -1);
    return bookService.getRecentBooksData(cal.getTime(), new Date(), rowLimit);
  }

  @ModelAttribute("popularBooks")
  public List<Book> popularBooks() {
    return bookService.getBooksData(rowLimit);
  }

  @GetMapping("/")
  public String mainPage(Model model) {
    model.addAttribute("tags", tagService.getAll());
    return "index";
  }

  @GetMapping("/favicon.ico")
  public void redirectWithUsingRedirectPrefix(ModelMap model, HttpServletResponse response) throws Exception {
    Resource serverFile = new ClassPathResource("spring-frontend/logo.ico");

    if (serverFile.exists()) {
      InputStream fsIo = serverFile.getInputStream();
      try {
        response.addHeader("Content-Type", "image/x-icon");
        response.addHeader("Content-Length", String.valueOf(serverFile.getFile().length()));
        IOUtils.copy(fsIo, response.getOutputStream());
        response.getOutputStream().flush();
      } finally {
        fsIo.close();
      }
    } else {
      throw new Exception("File " + serverFile.getFile().getAbsolutePath() + " not found");
    }
  }
}
