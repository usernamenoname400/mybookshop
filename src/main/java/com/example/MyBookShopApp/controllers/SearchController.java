package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookService;
import com.example.MyBookShopApp.data.BooksPageDTO;
import com.example.MyBookShopApp.data.SearchWordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SearchController {
  private final BookService bookService;

  @Value("${sql.rowlimit}")
  private int rowLimit;

  @Autowired
  public SearchController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping("/search")
  public String mainPage(Model model) {
    return "search/index";
  }

  @GetMapping(value = "/search/{searchWord}")
  public String getSearchResult(
      @PathVariable("searchWord") SearchWordDto searchWord,
      @RequestParam(value = "offset", defaultValue = "0") Integer offset,
      @RequestParam(value = "limit", defaultValue = "0") Integer limit,
      Model model
  ) {
    if (limit == 0) {
      limit = rowLimit;
    }
    model.addAttribute("searchWordDto", searchWord);
    model.addAttribute(
        "searchResults",
           bookService.getPageOfSearchResult(searchWord.getExample(), offset, limit).getContent()
    );
    return "search/index";
  }

  @GetMapping("/search/page/{searchWord}")
  @ResponseBody
  public BooksPageDTO getNextSearchPage(
      @RequestParam("offset") Integer offset,
      @RequestParam("limit") Integer limit,
      @PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto
  ) {
    return new BooksPageDTO(bookService.getPageOfSearchResult(searchWordDto.getExample(), offset, limit).getContent());
  }
}
