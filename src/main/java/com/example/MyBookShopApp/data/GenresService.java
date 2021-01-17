package com.example.MyBookShopApp.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GenresService {
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public GenresService(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Genres> getSuperGenresList() {
    List<Genres> genres =
        jdbcTemplate.query(
            "select g.super_genre name, g.super_id id, count(b.id) book_qnt " +
            "from genres g " +
            "left join books b on g.id = b.genres_id " +
            "group by g.super_id, g.super_genre",
            (ResultSet rs, int rowNum) ->
                new Genres(
                    rs.getInt("id"),
                    rs.getString("name"),
                    "",
                    -1,
                    rs.getInt("book_qnt")
                )
        );
    return new ArrayList<>(genres);
  }

  public List<Genres> getGenresList() {
    List<Genres> genres =
        jdbcTemplate.query(
            "select g.id, g.name genres_name, g.super_genre, g.super_id, count(b.id) book_qnt " +
            "from genres g " +
            "left join books b on g.id = b.genres_id " +
            "group by g.id, g.name, g.super_genre",
            (ResultSet rs, int rowNum) ->
                new Genres(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("super_genre"),
                    rs.getInt("super_id"),
                    rs.getInt("book_qnt")
                )
        );
    return new ArrayList<>(genres);
  }

  public Map<Integer, List<Genres>> getGenresMap() {
    List<Genres> genres = getGenresList();

    return genres.stream().collect(Collectors.groupingBy((Genres g) -> g.getSuperId()));
  }

  public Genres getGenres(Integer genresId) {
    List<Genres> genres =
        jdbcTemplate.query(
            "select g.id, g.name genres_name, g.super_genre, g.super_id, count(b.id) book_qnt " +
            "from genres g " +
            "left join books b on g.id = b.genres_id " +
            "where g.id = ? " +
            "group by g.id, g.name, g.super_genre",
            (ResultSet rs, int rowNum) ->
                new Genres(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("super_genre"),
                    rs.getInt("super_id"),
                    rs.getInt("book_qnt")
                ),
            new Object[]{genresId}
        );
    if (genres.size() > 0) {
      return genres.get(0);
    }
    return new Genres(-1, "", "", -1, 0);
  }

  public Genres getSuperGenres(Integer genresId) {
    List<Genres> genres =
        jdbcTemplate.query(
            "select distinct g.super_genre name, g.super_id id, count(b.id) book_qnt " +
            "from genres g " +
            "left join books b on g.id = b.genres_id " +
            "where g.id = ? " +
            "group by g.id, g.name, g.super_genre",
            (ResultSet rs, int rowNum) ->
                new Genres(
                    rs.getInt("id"),
                    rs.getString("name"),
                    "",
                    -1,
                    rs.getInt("book_qnt")
                ),
            new Object[]{genresId}
        );
    if (genres.size() > 0) {
      return genres.get(0);
    }
    return new Genres(-1, "", "", -1, 0);
  }
}
