package com.example.MyBookShopApp.data.service;

import com.example.MyBookShopApp.data.dto.Book;
import com.example.MyBookShopApp.data.dto.Genre;
import com.example.MyBookShopApp.data.repository.BookRepository;
import com.example.MyBookShopApp.data.repository.GenreRepository;
import com.example.MyBookShopApp.errors.BookstoreApiWrongParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
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

  public Book getBookBySlug(String slug) {
    Optional<Book> bookOpt = bookRepository.findBySlug(slug);
    if (bookOpt.isPresent()) {
      return bookRepository.findBySlug(slug).get();
    } else {
      return null;
    }
  }

  public void SaveBook(Book book) {
    bookRepository.save(book);
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

  public List<Book> getBooksByTitle(String bookTitle) throws BookstoreApiWrongParameterException {
    if (bookTitle == null || bookTitle.equals("") || bookTitle.length() < 1) {
      throw new BookstoreApiWrongParameterException("Wrong values passed to one or more parameters");
    }
    List<Book> data = bookRepository.findBookByTitleContaining(bookTitle);
    if (data.size() == 0) {
      throw new BookstoreApiWrongParameterException("No books found for title '" + bookTitle + "'");
    }
    return data;
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

  public List<Book> getBooksBySlugs(String[] slugs) {
    return bookRepository.findBooksBySlugIn(slugs);
  }
}
