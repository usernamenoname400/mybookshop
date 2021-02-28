package com.example.MyBookShopApp.data.dto;

import java.util.List;

public class BooksPageDTO {
  private Integer count;
  private List<Book> books;

  public BooksPageDTO(List<Book> books) {
    this.books = books;
    this.count = books.size();
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public List<Book> getBooks() {
    return books;
  }

  public void setBooks(List<Book> books) {
    this.books = books;
  }
}
