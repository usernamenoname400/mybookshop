package com.example.MyBookShopApp.data.service;

import com.example.MyBookShopApp.data.dto.BookReview;
import com.example.MyBookShopApp.data.repository.BookReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookReviewService {
  private final BookReviewRepository bookReviewRepository;

  @Autowired
  public BookReviewService(BookReviewRepository bookReviewRepository) {
    this.bookReviewRepository = bookReviewRepository;
  }

  public void saveReview(BookReview review) {
    bookReviewRepository.save(review);
  }
}
