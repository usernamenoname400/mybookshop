package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.data.dto.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReviewRepository extends JpaRepository<BookReview, Integer> {

}
