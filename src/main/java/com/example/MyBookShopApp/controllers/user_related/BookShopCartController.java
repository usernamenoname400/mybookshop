package com.example.MyBookShopApp.controllers.user_related;

import com.example.MyBookShopApp.data.dto.Book;
import com.example.MyBookShopApp.data.service.BookService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
public class BookShopCartController {
  private final BookService bookService;

  @Autowired
  public BookShopCartController(BookService bookService) {
    this.bookService = bookService;
  }

  @ModelAttribute(name = "bookCart")
  public List<Book> bookCart() {
    return new ArrayList<>();
  }

  @GetMapping("/cart")
  public String getCart(
      @CookieValue(value = "cartContents", required = false) String cartContents,
      Model model
  ) {
    if (cartContents == null || cartContents.equals("")) {
      model.addAttribute("isCartEmpty", "true");
      model.addAttribute("cartBooks", new ArrayList<Book>());
    } else {
      model.addAttribute("isCartEmpty", "false");
      cartContents = StringUtils.removeStart(cartContents, "/");
      cartContents = StringUtils.removeEnd(cartContents, "/");
      String[] cookieSlugs = cartContents.split("/");
      model.addAttribute("cartBooks", bookService.getBooksBySlugs(cookieSlugs));
    }
    return "cart";
  }

  @PostMapping("/changeBookStatus/removeCart/{slug}")
  public String handleRemoveBookFromCart(
      @PathVariable("slug") String slug,
      @CookieValue(name = "cartContents", required = false) String cartContents,
      HttpServletResponse response,
      Model model
  ) {
    if(cartContents != null || !cartContents.equals("")) {
      ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(cartContents.split("/")));
      cookieBooks.remove(slug);
      Cookie cookie = new Cookie("cartContents", String.join("/", cookieBooks));
      cookie.setPath("/");
      response.addCookie(cookie);
      model.addAttribute("isCartEmpty", false);
    } else {
      model.addAttribute("isCartEmpty", true);
    }

    return "redirect:/cart";
  }

  @PostMapping("/changeBookStatus/addCart/{slug}")
  public String handleChangeBookStatus(
      @PathVariable("slug") String slug,
      @CookieValue(name = "cartContents", required = false) String cartContents,
      @CookieValue(name = "postponedContents", required = false) String postponedContents,
      HttpServletResponse response,
      Model model
  ) {
    if(cartContents == null || cartContents.equals("")) {
      Cookie cartCookie = new Cookie("cartContents", slug);
      cartCookie.setPath("/");
      response.addCookie(cartCookie);
    } else if (!cartContents.contains(slug)){
      StringJoiner stringJoiner = new StringJoiner("/");
      stringJoiner.add(cartContents).add(slug);
      Cookie cartCookie = new Cookie("cartContents", stringJoiner.toString());
      cartCookie.setPath("/");
      response.addCookie(cartCookie);
    }
    // При добавлении в корзину удаляем из отложенных
    if (postponedContents != null && postponedContents.contains(slug)) {
      ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(postponedContents.split("/")));
      cookieBooks.remove(slug);
      Cookie postponeCookie = new Cookie("postponedContents", String.join("/", cookieBooks));
      postponeCookie.setPath("/");
      response.addCookie(postponeCookie);
    }

    model.addAttribute("isCartEmpty", false);

    return "redirect:/books/" + slug;
  }

  @PostMapping("/changeBookStatus/buyAll")
  public String handleBuyAll(
      @CookieValue(name = "cartContents", required = false) String cartContents,
      @CookieValue(name = "postponedContents", required = false) String postponedContents,
      HttpServletResponse response,
      Model model
  )
  {
    Logger.getLogger(this.getClass().getSimpleName()).info("postponed=" + postponedContents);
    Logger.getLogger(this.getClass().getSimpleName()).info("cart=" + cartContents);
    if (postponedContents != null && !postponedContents.isEmpty()) {
      List<String> inCart = new ArrayList<>();
      if (cartContents != null && !cartContents.isEmpty()) {
        inCart.addAll(new ArrayList<>(Arrays.asList(cartContents.split("/"))));
      }
      Logger.getLogger(this.getClass().getSimpleName()).info("cart qnt=" + inCart.size());

      ArrayList<String> postponedBooks = new ArrayList<>(Arrays.asList(postponedContents.split("/")));
      Logger.getLogger(this.getClass().getSimpleName()).info("postponed qnt=" + postponedBooks.size());
      inCart.addAll(postponedBooks);
      inCart = inCart.stream().distinct().collect(Collectors.toList());
      Logger.getLogger(this.getClass().getSimpleName()).info("cart qnt=" + inCart.size());

      Cookie postponeCookie = new Cookie("postponedContents", "");
      postponeCookie.setPath("/");
      response.addCookie(postponeCookie);

      Cookie cartCookie = new Cookie("cartContents", String.join("/", inCart));
      cartCookie.setPath("/");
      response.addCookie(cartCookie);
    }

    model.addAttribute("isPostponedEmpty", true);

    return "redirect:/postponed";
  }
}
