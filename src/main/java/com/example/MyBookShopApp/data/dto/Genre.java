package com.example.MyBookShopApp.data.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="genre")
public class Genre {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  @ManyToOne(cascade = CascadeType.PERSIST)
  private Genre parent;
  @OneToMany(mappedBy = "genre")
  @JsonBackReference
  private List<Book> bookList = new ArrayList<>();
  @OneToMany(mappedBy = "parent")
  private List<Genre> childGenres;

  @Override
  public String toString() {
    return "Genres{" +
           "id=" + id +
           ", name='" + name + '\'' +
           ", parentId='" + ((parent == null) ? "": parent.id) + '\'' +
           '}';
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Genre getParent() {
    return parent;
  }

  public void setParent(Genre parent) {
    this.parent = parent;
  }

  public List<Book> getBookList() {
    return bookList;
  }

  public void setBookList(List<Book> bookList) {
    this.bookList = bookList;
  }

  public Integer getBookCount() {
    Integer result = bookList.size();

    if (childGenres != null) {
      result += childGenres.stream().map(genres -> genres.getBookCount()).reduce(0, Integer::sum);
    }
    return result;
  }
}
