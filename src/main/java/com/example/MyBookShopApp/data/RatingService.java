package com.example.MyBookShopApp.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RatingService {
  private final RatingRepository ratingRepository;

  @Autowired
  public RatingService(RatingRepository ratingRepository) {
    this.ratingRepository = ratingRepository;
  }

  public Rating getBookRating(int bookId) {
    Rating rating = ratingRepository.findById(bookId).get();
    if (rating != null) {
      return rating;
    }
    return new Rating();
  }

  public Integer ratingByBook(int bookId) {
    Rating rating = getBookRating(bookId);
    return rating.getRatingAvg();
  }
}
