package com.example.MyBookShopApp.data;

public class Genres {
  private Integer id;
  private String name;
  private String superGenre;
  private Integer superId;
  private Integer bookCount;

  public Genres(Integer id, String name, String superGenre, Integer superId, Integer bookCount) {
    this.id = id;
    this.name = name;
    this.superGenre = superGenre;
    this.superId = superId;
    this.bookCount = bookCount;
  }

  @Override
  public String toString() {
    return "Genres{" +
           "id=" + id +
           ", name='" + name + '\'' +
           ", superGenre='" + superGenre + '\'' +
           ", superId=" + superId +
           ", bookCount=" + bookCount +
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

  public String getSuperGenre() {
    return superGenre;
  }

  public void setSuperGenre(String superGenre) {
    this.superGenre = superGenre;
  }

  public Integer getBookCount() {
    return bookCount;
  }

  public void setBookCount(Integer bookCount) {
    this.bookCount = bookCount;
  }

  public Integer getSuperId() {
    return superId;
  }

  public void setSuperId(Integer superId) {
    this.superId = superId;
  }
}
