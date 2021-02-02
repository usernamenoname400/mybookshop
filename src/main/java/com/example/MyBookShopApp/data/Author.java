package com.example.MyBookShopApp.data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String firstName;
  private String lastName;
  private String biographyShort;
  private String biographyRest;
  private String photo;

  @OneToMany(mappedBy = "author")
  private List<Book> bookList = new ArrayList<>();

  @Override
  public String toString() {
    return "Author{" + firstName + ' ' + lastName + '}';
  }

  public List<Book> getBookList() {
    return bookList;
  }

  public void setBookList(List<Book> bookList) {
    this.bookList = bookList;
  }

  public String getName() {
    return firstName + ' ' + lastName;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getBiographyShort() {
    return biographyShort;
  }

  public void setBiographyShort(String biographyShort) {
    this.biographyShort = biographyShort;
  }

  public String getBiographyRest() {
    return biographyRest;
  }

  public void setBiographyRest(String biographyRest) {
    this.biographyRest = biographyRest;
  }

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }
}
