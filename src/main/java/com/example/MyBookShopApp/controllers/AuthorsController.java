package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.AuthorService;
import com.example.MyBookShopApp.data.BookService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

@Controller
public class AuthorsController {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final AuthorService authorService;
  private final BookService bookService;

  @Autowired
  public AuthorsController(AuthorService authorService, BookService bookService) {
    this.authorService = authorService;
    this.bookService = bookService;
  }

  @GetMapping("/authors")
  public String getAuthors(Model model) {
    model.addAttribute("authorsMap", authorService.getAuthorsMap());
    return "authors/index";
  }

  @GetMapping("/authors/{authorId:\\d+}")
  public String getAuthorPage(@PathVariable Integer authorId, Model model) {
    model.addAttribute("authorData", authorService.getAuthorData(authorId));
    model.addAttribute("authorBooks", bookService.getBooksByAuthor(authorId));
    return "authors/slug";
  }

  @GetMapping("/books/author/{authorId:\\d+}")
  public String getAuthorBooksPage(@PathVariable Integer authorId, Model model) {
    model.addAttribute("authorData", authorService.getAuthorData(authorId));
    model.addAttribute("authorBooks", bookService.getBooksByAuthor(authorId));
    return "books/author";
  }

  @RequestMapping("/authors/img/{name:\\w+\\.+\\w+}")
  public void getFile(@PathVariable("name") String name, HttpServletResponse response) throws Exception {
    Resource serverFile = authorService.getImageFile(name);

    if (serverFile.exists()) {
      InputStream fsIo = serverFile.getInputStream();
      try {
        switch (name.substring(name.lastIndexOf('.') + 1)) {
          case "png" : response.addHeader("Content-Type", MediaType.IMAGE_PNG_VALUE); break;
          case "jpg" : response.addHeader("Content-Type", MediaType.IMAGE_JPEG_VALUE); break;
          case "gif" : response.addHeader("Content-Type", MediaType.IMAGE_GIF_VALUE); break;
          default: response.addHeader("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
        }
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
