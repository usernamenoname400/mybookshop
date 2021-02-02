package com.example.MyBookShopApp.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
  private final BookRepository bookRepository;
  private final GenresRepository genresRepository;

  @Autowired
  public BookService(BookRepository bookRepository, GenresRepository genresRepository) {
    this.bookRepository = bookRepository;
    this.genresRepository = genresRepository;
  }

  public List<Book> getBooksData(int limitRows) {
    return bookRepository.findAll();
  }

  public List<Book> getBooksCart() {
    return bookRepository.findAll();
  }

  public List<Book> getBooksPostponed() {
    return bookRepository.findTop3ByOrderByTitle();
  }

  public Book getBookData(int bookId) {
    return bookRepository.findById(bookId).get();
  }

  public List<Book> getRecentBooksData(Date dateFrom, Date dateTo, int limitRows) {
    return bookRepository.findAll();
  }

  public List<Book> getBooksByAuthor(Integer authorId) {
    return bookRepository.findByAuthor_Id(authorId);
  }

  public List<Book> getBooksByGenre(Integer genreId) {
    return bookRepository.findByGenres_Id(genreId);
  }

  public List<Book> getBooksBySuperGenre(Integer genreId) {
    List<Genres> genres = genresRepository.findAllByParent_Id(genreId);
    List<Integer> renresIds = genres.stream().map(Genres::getId).collect(Collectors.toList());
    return bookRepository.findInRootGenres(renresIds);
  }
}
