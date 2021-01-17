package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.LocalProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorService {
  private final JdbcTemplate jdbcTemplate;
  //private final List<String> images;
  private final LocalProperties localProperties;
  private final ResourceLoader resourceLoader;

  @Autowired
  public AuthorService(JdbcTemplate jdbcTemplate, LocalProperties localProperties, ResourceLoader resourceLoader) {
    this.localProperties = localProperties;
    this.resourceLoader = resourceLoader;
    this.jdbcTemplate = jdbcTemplate;
    /*this.images =
        jdbcTemplate.query(
            "select distinct photo from authors",
            (ResultSet rs, int rowNum) -> rs.getString("photo")
        );*/
  }

  public Author getAuthorData(Integer authorId) {
    Author author =
        jdbcTemplate.queryForObject(
            "select * from authors where id = ?",
             (ResultSet rs, int rowNum) ->
               new Author(
                   rs.getInt("id"),
                   rs.getString("first_name"),
                   rs.getString("last_name"),
                   "",
                   "",
                   ""
               ),
            new Object[]{authorId}
        );

    return author;
  }

  public Map<String, List<Author>> getAuthorsMap() {
     List<Author> authors =
        jdbcTemplate.query(
            "select * from authors a order by a.last_name",
            (ResultSet rs, int rownum) ->
                new Author(
                    rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    "",
                    "",
                    ""
                )
        );

    return authors.stream().collect(Collectors.groupingBy((Author a) -> a.getLastName().substring(0, 1) ));
  }

  public boolean isPhotoExists(String photo) {
    return true; //this.images.contains(photo);
  }

  public Resource getImageFile(String photo) {
    if (true/*this.images.contains(photo)*/) {
      return new ClassPathResource(localProperties.getImagesPath() + File.separator + photo);
    } else {
      return new ClassPathResource(localProperties.getPlaceholderPath() + File.separator + "author.jpg");
    }
  }
}
