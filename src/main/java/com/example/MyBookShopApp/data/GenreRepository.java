package com.example.MyBookShopApp.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Integer> {

  @Query(value = "from Genre where parent is null")
  List<Genre> findRoot();

  @Query(value = "from Genre where parent is not null")
  List<Genre> findRegular();

  List<Genre> findAllByParent_Id(Integer parentId);

}
