package com.example.MyBookShopApp.controllers.user_related;

import com.example.MyBookShopApp.data.dto.Book;
import com.example.MyBookShopApp.data.service.BookService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

@Controller
public class BookPostponedController {
  private final BookService bookService;

  public BookPostponedController(BookService bookService) {
    this.bookService = bookService;
  }

  @ModelAttribute(name = "bookCart")
  public List<Book> bookCart() {
    return new ArrayList<>();
  }

  @GetMapping("/postponed")
  public String getPostponed(
      @CookieValue(value = "postponedContents", required = false) String postponedContents,
      Model model
  ) {
    if (postponedContents == null || postponedContents.equals("")) {
      model.addAttribute("isPostponedEmpty", "true");
      model.addAttribute("postponedBooks", new ArrayList<Book>());
    } else {
      model.addAttribute("isPostponedEmpty", "false");
      postponedContents = StringUtils.removeStart(postponedContents, "/");
      postponedContents = StringUtils.removeEnd(postponedContents, "/");
      String[] cookieSlugs = postponedContents.split("/");
      model.addAttribute("postponedBooks", bookService.getBooksBySlugs(cookieSlugs));
    }

    return "postponed";
  }

  @PostMapping("/changeBookStatus/removePostpone/{slug}")
  public String handleRemoveBookFromCart(
      @PathVariable("slug") String slug,
      @CookieValue(name = "postponedContents", required = false) String postponedContents,
      HttpServletResponse response,
      Model model
  ) {
    if(postponedContents != null || !postponedContents.equals("")) {
      ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(postponedContents.split("/")));
      cookieBooks.remove(slug);
      Cookie cookie = new Cookie("postponedContents", String.join("/", cookieBooks));
      cookie.setPath("/");
      response.addCookie(cookie);
      model.addAttribute("isPostponedEmpty", false);
    } else {
      model.addAttribute("isPostponedEmpty", true);
    }

    return "redirect:/postponed";
  }

  @PostMapping("/changeBookStatus/postpone/{slug}")
  public String handleChangeBookStatus(
      @PathVariable("slug") String slug,
      @CookieValue(name = "postponedContents", required = false) String postponedContents,
      HttpServletResponse response,
      Model model
  ) {
    if(postponedContents == null || postponedContents.equals("")) {
      Cookie cookie = new Cookie("postponedContents", slug);
      cookie.setPath("/");
      response.addCookie(cookie);
      model.addAttribute("isPostponedEmpty", false);
    } else if (!postponedContents.contains(slug)){
      StringJoiner stringJoiner = new StringJoiner("/");
      stringJoiner.add(postponedContents).add(slug);
      Cookie cookie = new Cookie("postponedContents", stringJoiner.toString());
      cookie.setPath("/");
      response.addCookie(cookie);
      model.addAttribute("isPostponedEmpty", false);
    }
    return "redirect:/books/" + slug;
  }

}
