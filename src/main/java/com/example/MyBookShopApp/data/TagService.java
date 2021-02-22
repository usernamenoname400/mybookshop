package com.example.MyBookShopApp.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
  private final BookRepository bookRepository;
  private final TagRepository tagRepository;

  @Autowired
  public TagService(BookRepository bookRepository, TagRepository tagRepository) {
    this.bookRepository = bookRepository;
    this.tagRepository = tagRepository;
  }

  public List<Tag> getAll() {
    return tagRepository.findAll();
  }

  public Tag getTag(Integer tagId) {
    return tagRepository.findById(tagId).get();
  }

  public Page<Book> getBooksByTag(Integer tagId, Integer page, Integer limit) {
    Pageable nextPage = PageRequest.of(page, limit);
    return bookRepository.findBooksByTag(tagId, nextPage);
  }
}
