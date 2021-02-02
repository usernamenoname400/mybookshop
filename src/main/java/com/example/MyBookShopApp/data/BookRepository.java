package com.example.MyBookShopApp.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Integer> {

  @Query(value = "from Book")
  List<Book> findBooks();

  List<Book> findTop3ByOrderByTitle();

  List<Book> findByAuthor_Id(Integer authorId);

  List<Book> findByGenres_Id(Integer genresId);

  @Query(value = "from Book as b where b.genres.id in ?1")
  List<Book> findInRootGenres(List<Integer> genres);
}
