package com.example.MyBookShopApp.data.service;

import com.example.MyBookShopApp.data.dto.Author;
import com.example.MyBookShopApp.data.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorService {
  private final AuthorRepository authorRepository;

  @Autowired
  public AuthorService(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  public Author getAuthorData(Integer authorId) {
    return authorRepository.findById(authorId).get();
   }

  public Map<String, List<Author>> getAuthorsMap() {
    List<Author> authors = authorRepository.findAll();
    return authors.stream().collect(Collectors.groupingBy((Author a) -> a.getLastName().substring(0, 1) ));
  }

}
