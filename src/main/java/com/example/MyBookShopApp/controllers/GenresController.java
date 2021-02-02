package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.BookService;
import com.example.MyBookShopApp.data.GenresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GenresController {
  private final GenresService genresService;
  private final BookService bookService;

  @Autowired
  public GenresController(GenresService genresService, BookService bookService) {
    this.genresService = genresService;
    this.bookService = bookService;
  }

  @GetMapping("/genres")
  public String getGenres(Model model) {
    model.addAttribute("superGenres", genresService.getSuperGenresList());
    model.addAttribute("genresMap", genresService.getGenresMap());
    return "genres/index";
  }

  @GetMapping("/genres/{genresId:\\d+}")
  public String getGenrePage(@PathVariable Integer genresId, Model model) {
    model.addAttribute("genre", genresService.getGenres(genresId));
    model.addAttribute("booksGenre", bookService.getBooksByGenre(genresId));
    return "genres/slug";
  }

  @GetMapping("/genres/super/{genresId:\\d+}")
  public String getSuperGenrePage(@PathVariable Integer genresId, Model model) {
    model.addAttribute("genre", genresService.getGenres(genresId));
    model.addAttribute("booksGenre", bookService.getBooksBySuperGenre(genresId));
    return "genres/slug";
  }

}
