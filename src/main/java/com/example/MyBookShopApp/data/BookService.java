package com.example.MyBookShopApp.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
  private final BookRepository bookRepository;
  private final GenreRepository genresRepository;
  @Value("${sql.rowlimit}")
  private int rowLimit;

  @Autowired
  public BookService(BookRepository bookRepository, GenreRepository genresRepository) {
    this.bookRepository = bookRepository;
    this.genresRepository = genresRepository;
  }

  public List<Book> getBooksPostponed() {
    return bookRepository.findTop3ByOrderByTitle();
  }

  public Book getBookData(int bookId) {
    return bookRepository.findById(bookId).get();
  }

  public List<Book> getRecentBooksData(Date dateFrom, Date dateTo, int limitRows) {
    return bookRepository.getBookByPubDateBetween(dateFrom, dateTo);
  }

  public Page<Book> getBooksByAuthor(Integer authorId, Integer page, Integer limit) {
    Pageable nextPage = PageRequest.of(page, limit);
    return bookRepository.findByAuthor_Id(authorId, nextPage);
  }

  public Long getBookCountByAuthor(Integer authorId) {
    return bookRepository.countByAuthor_Id(authorId);
  }

  public Page<Book> getBooksByGenre(Integer genreId, Integer page, Integer limit) {
    Pageable nextPage = PageRequest.of(page, limit);
    return bookRepository.findByGenre_Id(genreId, nextPage);
  }

  public Page<Book> getBooksBySuperGenre(Integer genreId, Integer offset, Integer limit) {
    List<Genre> genres = genresRepository.findAllByParent_Id(genreId);
    List<Integer> genreIds = genres.stream().map(Genre::getId).collect(Collectors.toList());
    Pageable nextPage = PageRequest.of(offset, limit);
    return bookRepository.findInRootGenres(genreIds, nextPage);
  }

  // NEW BOOK SERVICE METHODS

  public List<Book> getBooksByAuthor(String authorName) {
    return bookRepository.findBooksByAuthorFirstNameContaining(authorName);
  }

  public List<Book> getBooksByTitle(String bookTitle) {
    return bookRepository.findBookByTitleContaining(bookTitle);
  }

  public List<Book> getBooksWithPriceBetween(Integer min, Integer max) {
    return bookRepository.findBooksByPriceBetween(min, max);
  }

  public List<Book> getBooksWithPrice(Integer price) {
    return bookRepository.findBooksByPriceIs(price);
  }

  public List<Book> getBooksWithMaxDiscount() {
    return bookRepository.getBooksWithMaxDiscount();
  }

  public List<Book> getBestSellers() {
    return bookRepository.getBestSellers();
  }

  public Page<Book> getPageRecomendedBooks(Integer page, Integer limit) {
    Pageable nextPage = PageRequest.of(page, limit);
    return bookRepository.findAll(nextPage);
  }

  public Page<Book> getPageOfSearchResult(String query, Integer page, Integer limit) {
    Pageable nextPage = PageRequest.of(page, limit);
    return bookRepository.findBookByTitleContaining(query, nextPage);
  }

  public Page<Book> getPageOfRecent(Date from, Date to, Integer page, Integer limit) {
    Pageable nextPage = PageRequest.of(page, limit);
    return bookRepository.findBookByPubDateBetween(from, to, nextPage);
  }

  public Page<Book> getPageOfPopular(Integer page, Integer limit) {
    Pageable nextPage = PageRequest.of(page, limit);
    return bookRepository.findAllByOrderByRatingDesc(nextPage);
  }
}
