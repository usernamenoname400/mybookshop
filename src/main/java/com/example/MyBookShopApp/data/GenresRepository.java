package com.example.MyBookShopApp.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GenresRepository extends JpaRepository<Genres, Integer> {

  @Query(value = "from Genres where parent is null")
  List<Genres> findRoot();

  @Query(value = "from Genres where parent is not null")
  List<Genres> findRegular();

  List<Genres> findAllByParent_Id(Integer parentId);

}
