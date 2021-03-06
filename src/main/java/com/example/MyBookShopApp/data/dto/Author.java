package com.example.MyBookShopApp.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "author")
@ApiModel(description = "data model of author  entity")
public class Author {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ApiModelProperty(value = "author id generated by db", position = 1)
  private Integer id;
  @ApiModelProperty(value = "first name of author", example = "Bob", position = 2)
  private String firstName;
  @ApiModelProperty(value = "last name of author", example = "Marley", position = 3)
  private String lastName;
  private String biographyShort;
  private String biographyRest;
  private String photo;

  /*@OneToMany(mappedBy = "author")
  @JsonIgnore
  private List<Book> bookList = new ArrayList<>();*/

  @Override
  public String toString() {
    return "Author{" + firstName + ' ' + lastName + '}';
  }

  /*public List<Book> getBookList() {
    return bookList;
  }

  public void setBookList(List<Book> bookList) {
    this.bookList = bookList;
  }*/

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
