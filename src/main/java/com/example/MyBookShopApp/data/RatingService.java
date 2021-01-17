package com.example.MyBookShopApp.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.List;

@Service
public class RatingService {
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public RatingService(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public Rating getBookRating(int bookId) {
    List<Rating> ratings =
        jdbcTemplate.query(
            "select r.* from ratings r where r.book_id = ?",
            (ResultSet rs, int rowNum) ->
                new Rating(
                    rs.getInt("book_id"),
                    rs.getInt("rating_cnt_1"),
                    rs.getInt("rating_cnt_2"),
                    rs.getInt("rating_cnt_3"),
                    rs.getInt("rating_cnt_4"),
                    rs.getInt("rating_cnt_5")
                ),
            new Object[]{bookId}
        );
    if (ratings.size() >= 1) {
      return ratings.get(0);
    }
    return new Rating(bookId, 0, 0, 0, 0, 0);
  }

  public Integer ratingByBook(int bookId) {
    Rating rating = getBookRating(bookId);
    return rating.getRatingAvg();
  }
}
