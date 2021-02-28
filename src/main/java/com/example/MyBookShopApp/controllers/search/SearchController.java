package com.example.MyBookShopApp.controllers.search;

import com.example.MyBookShopApp.data.service.BookService;
import com.example.MyBookShopApp.data.dto.BooksPageDTO;
import com.example.MyBookShopApp.data.dto.SearchWordDto;
import com.example.MyBookShopApp.errors.EmptySearchException;
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

  /*@GetMapping("/search")
  public String mainPage(Model model) {
    return "search/index";
  }*/

  @GetMapping(value = {"/search", "/search/{searchWord}"})
  public String getSearchResult(
      @PathVariable(value = "searchWord", required = false) SearchWordDto searchWord,
      Model model
  ) throws EmptySearchException
  {
    if (searchWord != null) {
      model.addAttribute("searchWordDto", searchWord);
      model.addAttribute(
          "searchResults",
          bookService.getPageOfSearchResult(searchWord.getExample(), 0, rowLimit).getContent()
      );
      return "search/index";
    }
    throw new EmptySearchException("Передан пустой запрос для поиска");
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
