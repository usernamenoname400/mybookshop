package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

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

  @GetMapping("/")
  public String mainPage(Model model) {
    model.addAttribute("tags", tagService.getAll());
    model.addAttribute("recommendedBooks", bookService.getPageRecomendedBooks(0, 6).getContent());
    model.addAttribute("popularBooks", bookService.getPageOfPopular(0, 6).getContent());

    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.MONTH, -1);
    model.addAttribute("recentBooks", bookService.getPageOfRecent(cal.getTime(), new Date(), 0, 6).getContent());

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

  @GetMapping("/recommended/page")
  @ResponseBody
  public BooksPageDTO getBookPage(@RequestParam("offset") Integer offset,
                                  @RequestParam("limit") Integer limit) {
    return new BooksPageDTO(bookService.getPageRecomendedBooks(offset, limit).getContent());
  }
}
