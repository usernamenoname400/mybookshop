package com.example.MyBookShopApp.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookService {
  private final JdbcTemplate jdbcTemplate;
  private final RatingService ratingService;

  @Autowired
  public BookService(JdbcTemplate jdbcTemplate, RatingService ratingService) {
    this.jdbcTemplate = jdbcTemplate;
    this.ratingService = ratingService;
  }

  public List<Book> getBooksData(int limitRows) {
    List<Book> books =
        jdbcTemplate.query(
            "select b.id, concat(a.first_name, ' ', a.last_name) author, b.title, b.priceOld, b.price, b.author_id " +
            "from books b " +
            "join authors a on a.id = b.author_id " +
            "left join ratings r on r.book_id = b.id " +
            (limitRows > 0 ? "limit " + limitRows : ""),
            (ResultSet rs, int rowNum) ->
               new Book(
                   rs.getInt("id"),
                   rs.getString("author"),
                   rs.getString("title"),
                   rs.getString("priceOld"),
                   rs.getString("price"),
                   ratingService.ratingByBook(rs.getInt("id")),
                   rs.getInt("author_id")
               )
        );
    return new ArrayList<>(books);
  }

  public List<Book> getBooksCart() {
    List<Book> books =
        jdbcTemplate.query(
            "select b.id, concat(a.first_name, ' ', a.last_name) author, b.title, b.priceOld, b.price, b.author_id " +
            "from books b " +
            "join authors a on a.id = b.author_id " +
            "limit 3",
            (ResultSet rs, int rowNum) ->
                new Book(
                    rs.getInt("id"),
                    rs.getString("author"),
                    rs.getString("title"),
                    rs.getString("priceOld"),
                    rs.getString("price"),
                    ratingService.ratingByBook(rs.getInt("id")),
                    rs.getInt("author_id")
                )
        );
    return new ArrayList<>(books);
  }

  public List<Book> getBooksPostponed() {
    List<Book> books =
        jdbcTemplate.query(
            "select b.id, concat(a.first_name, ' ', a.last_name) author, b.title, b.priceOld, b.price, b.author_id " +
            "from books b " +
            "join authors a on a.id = b.author_id " +
            "limit 3",
            (ResultSet rs, int rowNum) ->
                new Book(
                    rs.getInt("id"),
                    rs.getString("author"),
                    rs.getString("title"),
                    rs.getString("priceOld"),
                    rs.getString("price"),
                    ratingService.ratingByBook(rs.getInt("id")),
                    rs.getInt("author_id")
                )
        );
    return new ArrayList<>(books);
  }

  public Book getBookData(int bookId) {
    List<Book> books =
        jdbcTemplate.query(
            "select b.id, concat(a.first_name, ' ', a.last_name) author, b.title, b.priceOld, b.price, b.author_id " +
            "from books b " +
            "join authors a on a.id = b.author_id " +
            "where b.id = ?",
            (ResultSet rs, int rowNum) ->
                new Book(
                    rs.getInt("id"),
                    rs.getString("author"),
                    rs.getString("title"),
                    rs.getString("priceOld"),
                    rs.getString("price"),
                    ratingService.ratingByBook(rs.getInt("id")),
                    rs.getInt("author_id")
                ),
            new Object[]{bookId}
        );
    if (books.size() >= 1) {
      return books.get(0);
    }
    return null;
  }

  public List<Book> getRecentBooksData(Date dateFrom, Date dateTo, int limitRows) {
    List<Book> books =
        jdbcTemplate.query(
            "select b.id, concat(a.first_name, ' ', a.last_name) author, b.title, b.priceOld, b.price, b.author_id " +
            "from books b " +
            "join authors a on a.id = b.author_id " +
            "where b.dt_release > ? and dt_release < ? " +
            (limitRows > 0 ? "limit " + limitRows : ""),
            (ResultSet rs, int rowNum) ->
                new Book(
                    rs.getInt("id"),
                    rs.getString("author"),
                    rs.getString("title"),
                    rs.getString("priceOld"),
                    rs.getString("price"),
                    ratingService.ratingByBook(rs.getInt("id")),
                    rs.getInt("author_id")
                ),
            new Object[] {dateFrom, dateTo}
        );
    return new ArrayList<>(books);
  }

  public List<Book> getBooksByAuthor(Integer authorId) {
    List<Book> books =
        jdbcTemplate.query(
            "select b.id, concat(a.first_name, ' ', a.last_name) author, b.title, b.priceOld, b.price, b.author_id " +
            "from books b " +
            "join authors a on a.id = b.author_id " +
            "where a.id = ? " +
            "limit 20",
            (ResultSet rs, int rowNum) ->
                new Book(
                    rs.getInt("id"),
                    rs.getString("author"),
                    rs.getString("title"),
                    rs.getString("priceOld"),
                    rs.getString("price"),
                    ratingService.ratingByBook(rs.getInt("id")),
                    rs.getInt("author_id")
                ),
            new Object[]{authorId}
        );
    return new ArrayList<>(books);
  }

  public List<Book> getBooksByGenre(Integer genreId) {
    List<Book> books =
        jdbcTemplate.query(
            "select b.id, concat(a.first_name, ' ', a.last_name) author, b.title, b.priceOld, b.price, b.author_id " +
            "from books b " +
            "join authors a on a.id = b.author_id " +
            "where b.genres_id = ? ",
            (ResultSet rs, int rowNum) ->
                new Book(
                    rs.getInt("id"),
                    rs.getString("author"),
                    rs.getString("title"),
                    rs.getString("priceOld"),
                    rs.getString("price"),
                    ratingService.ratingByBook(rs.getInt("id")),
                    rs.getInt("author_id")
                ),
            new Object[]{genreId}
        );
    return new ArrayList<>(books);
  }

  public List<Book> getBooksBySuperGenre(Integer genreId) {
    List<Book> books =
        jdbcTemplate.query(
            "select b.id, concat(a.first_name, ' ', a.last_name) author, b.title, b.priceOld, b.price, b.author_id " +
            "from books b " +
            "join authors a on a.id = b.author_id " +
            "where b.genres_id in  (select id from genres where super_id = ?)",
            (ResultSet rs, int rowNum) ->
                new Book(
                    rs.getInt("id"),
                    rs.getString("author"),
                    rs.getString("title"),
                    rs.getString("priceOld"),
                    rs.getString("price"),
                    ratingService.ratingByBook(rs.getInt("id")),
                    rs.getInt("author_id")
                ),
            new Object[]{genreId}
        );
    return new ArrayList<>(books);
  }
}
