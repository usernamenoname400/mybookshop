package com.example.MyBookShopApp.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GenresService {
  private final GenresRepository genresRepository;

  @Autowired
  public GenresService(GenresRepository genresRepository) {
    this.genresRepository = genresRepository;
  }

  public List<Genres> getSuperGenresList() {
    return genresRepository.findRoot();
  }

  public Map<Integer, List<Genres>> getGenresMap() {
    List<Genres> genres = genresRepository.findRegular();

    if (genres != null && genres.size() > 0) {
      return genres.stream().collect(Collectors.groupingBy((Genres g) -> g.getParent().getId()));
    }
    return new HashMap<>();
  }

  public Genres getGenres(Integer genresId) {
    return genresRepository.findById(genresId).get();
  }
}
