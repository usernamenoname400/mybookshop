package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(description = "book data api")
public class BookRestApiController {
  private final BookService bookService;

  @Autowired
  public BookRestApiController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping("/books/by-author")
  @ApiOperation("Get book list of bookshop filtered by author name")
  public ResponseEntity<List<Book>> booksByAuthor(@RequestParam("author") String authorName) {
    return ResponseEntity.ok(bookService.getBooksByAuthor(authorName));
  }

  @GetMapping("/books/by-title")
  @ApiOperation("Get book list of bookshop filtered by book title")
  public ResponseEntity<List<Book>> booksByTitle(@RequestParam("title") String bookTitle) {
    return ResponseEntity.ok(bookService.getBooksByTitle(bookTitle));
  }

  @GetMapping("/books/by-price-range")
  @ApiOperation("Get book list of bookshop filtered by regular price range")
  public ResponseEntity<List<Book>> booksByPriceRange(@RequestParam("min") Integer min, @RequestParam("max") Integer max) {
    return ResponseEntity.ok(bookService.getBooksWithPriceBetween(min, max));
  }

  @GetMapping("/books/with-max-discount")
  @ApiOperation("Get book list of bookshop with maximum discount")
  public ResponseEntity<List<Book>> booksWithMaxPrice() {
    return ResponseEntity.ok(bookService.getBooksWithMaxDiscount());
  }

  @GetMapping("/books/bestsellers")
  @ApiOperation("Get book list of bookshop filtered bestsellers only")
  public ResponseEntity<List<Book>> booksBestsellers() {
    return ResponseEntity.ok(bookService.getBestSellers());
  }
}
