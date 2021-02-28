package com.example.MyBookShopApp.data.service;

import com.example.MyBookShopApp.data.dto.Book;
import com.example.MyBookShopApp.data.dto.Rating;
import com.example.MyBookShopApp.data.repository.BookRepository;
import com.example.MyBookShopApp.data.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class RatingService {
  private final RatingRepository ratingRepository;
  private final BookRepository bookRepository;

  @Autowired
  public RatingService(RatingRepository ratingRepository, BookRepository bookRepository) {
    this.ratingRepository = ratingRepository;
    this.bookRepository = bookRepository;
  }

  public Rating getBookRating(Integer bookId) {
    Optional<Rating> opt = ratingRepository.findById(bookId);
    if (opt.isPresent()) {
      return opt.get();
    }
    Optional<Book> optBook = bookRepository.findById(bookId);
    if (optBook.isPresent()) {
      Rating result = new Rating();
      result.setBook(optBook.get().getId());
      return result;
    }
    return null;
  }

  public Rating getBookRatingBySlug(String bookSlug) {
    Optional<Book> optBook = bookRepository.findBySlug(bookSlug);
    if (optBook.isPresent()) {
      Optional<Rating> opt = ratingRepository.findById(optBook.get().getId());
      if (opt.isPresent()) {
        return opt.get();
      }
      Rating result = new Rating();
      result.setBook(optBook.get().getId());
      return result;
    }
    return null;
  }

  public Integer ratingByBook(int bookId) {
    Rating rating = getBookRating(bookId);
    return rating.getRatingAvg();
  }

  public void saveRating(Rating rating) {
    ratingRepository.save(rating);
  }
}
