package com.example.MyBookShopApp.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GenreService {
  private final GenreRepository genresRepository;

  @Autowired
  public GenreService(GenreRepository genresRepository) {
    this.genresRepository = genresRepository;
  }

  public List<Genre> getSuperGenresList() {
    return genresRepository.findRoot();
  }

  public Map<Integer, List<Genre>> getGenresMap() {
    List<Genre> genres = genresRepository.findRegular();

    if (genres != null && genres.size() > 0) {
      return genres.stream().collect(Collectors.groupingBy((Genre g) -> g.getParent().getId()));
    }
    return new HashMap<>();
  }

  public Genre getGenre(Integer genresId) {
    return genresRepository.findById(genresId).get();
  }
}
