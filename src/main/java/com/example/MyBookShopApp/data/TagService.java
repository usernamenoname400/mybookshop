package com.example.MyBookShopApp.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
  private final TagRepository tagRepository;

  @Autowired
  public TagService(TagRepository tagRepository) {
    this.tagRepository = tagRepository;
  }

  public List<Tag> getAll() {
    return tagRepository.findAll();
  }

  public Tag getTag(Integer tagId) {
    return tagRepository.findById(tagId).get();
  }

  public Object getBooksByTag(Integer tagId) {
    return tagRepository.findBooksByTag(tagId);
  }
}
