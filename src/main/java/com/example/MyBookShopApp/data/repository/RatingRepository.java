package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.data.dto.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
