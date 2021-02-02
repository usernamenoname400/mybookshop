package com.example.MyBookShopApp.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository  extends JpaRepository<Tag, Integer> {
  @Query("select t.bookList from Tag t where t.id = ?1")
  List<Book> findBooksByTag(Integer tagid);
}
