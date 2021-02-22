package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.SearchWordDto;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class SearchAdvisor {

  @ModelAttribute("searchWordDto")
  public SearchWordDto searchWordDto() {
    return new SearchWordDto();
  }

  @ModelAttribute("searchResults")
  public List<Book> searchResults() {
    return new ArrayList<>();
  }
}
