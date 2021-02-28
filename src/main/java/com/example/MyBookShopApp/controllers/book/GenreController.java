package com.example.MyBookShopApp.controllers.book;

import com.example.MyBookShopApp.data.service.BookService;
import com.example.MyBookShopApp.data.dto.BooksPageDTO;
import com.example.MyBookShopApp.data.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GenreController {
  private final GenreService genresService;
  private final BookService bookService;
  @Value("${sql.rowlimit}")
  private int rowLimit;

  @Autowired
  public GenreController(GenreService genresService, BookService bookService) {
    this.genresService = genresService;
    this.bookService = bookService;
  }

  @GetMapping("/genres")
  public String getGenres(Model model) {
    model.addAttribute("superGenres", genresService.getSuperGenresList());
    model.addAttribute("genresMap", genresService.getGenresMap());
    return "genres/index";
  }

  @GetMapping("/genres/{genreId:\\d+}")
  public String getGenrePage(
      @PathVariable Integer genreId,
      Model model
  ) {
    model.addAttribute("genre", genresService.getGenre(genreId));
    model.addAttribute("booksGenre", bookService.getBooksByGenre(genreId, 0, rowLimit).getContent());
    return "genres/slug";
  }

  @GetMapping("/genres/page/{genreId:\\d+}")
  @ResponseBody
  public BooksPageDTO getNextSearchPage(
      @PathVariable Integer genreId,
      @RequestParam("offset") Integer offset,
      @RequestParam("limit") Integer limit
  ) {
    return new BooksPageDTO(bookService.getBooksByGenre(genreId, offset, limit).getContent());
  }

  @GetMapping("/genresroot/{genreId:\\d+}")
  public String getSuperGenrePage(
      @PathVariable Integer genreId,
      Model model
  ) {
    model.addAttribute("genre", genresService.getGenre(genreId));
    model.addAttribute("booksGenre", bookService.getBooksBySuperGenre(genreId, 0, rowLimit).getContent());
    return "genres/slug";
  }

  @GetMapping("/genresroot/page/{genreId:\\d+}")
  @ResponseBody
  public BooksPageDTO getNextSearchPageSuper(
      @PathVariable Integer genreId,
      @RequestParam("offset") Integer offset,
      @RequestParam("limit") Integer limit
  ) {
    return new BooksPageDTO(bookService.getBooksBySuperGenre(genreId, offset, limit).getContent());
  }

}
