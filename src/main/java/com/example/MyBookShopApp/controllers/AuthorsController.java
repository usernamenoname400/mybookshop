package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.service.AuthorService;
import com.example.MyBookShopApp.data.service.BookService;
import com.example.MyBookShopApp.data.dto.BooksPageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthorsController {
  private final AuthorService authorService;
  private final BookService bookService;
  @Value("${sql.rowlimit}")
  private int rowLimit;

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
    model.addAttribute("authorBooks", bookService.getBooksByAuthor(authorId, 0, 6).getContent());
    model.addAttribute("authorBooksCount", bookService.getBookCountByAuthor(authorId));
    return "authors/slug";
  }

  @GetMapping("/authorbooks/{authorId:\\d+}")
  public String getAuthorBooksPage(@PathVariable Integer authorId, Model model) {
    model.addAttribute("authorData", authorService.getAuthorData(authorId));
    model.addAttribute("authorBooks", bookService.getBooksByAuthor(authorId, 0, rowLimit).getContent());
    return "books/author";
  }

  @GetMapping("/authorbooks/page/{authorId:\\d+}")
  @ResponseBody
  public BooksPageDTO getNextSearchPage(
      @PathVariable Integer authorId,
      @RequestParam("offset") Integer offset,
      @RequestParam("limit") Integer limit
  ) {
    return new BooksPageDTO(bookService.getBooksByAuthor(authorId, offset, limit).getContent());
  }

  @RequestMapping("/authors/img/{name:\\w+\\.+\\w+}")
  @ResponseBody
  public void getFile(@PathVariable("name") String name, HttpServletResponse response) throws Exception {
    /*Resource serverFile = authorService.getImageFile(name);

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
    }*/
  }
}
