package com.example.MyBookShopApp.data.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name="rating")
public class Rating {
  @Id
  @Column(name = "book_id")
  private Integer bookId;
  @OneToOne
  @MapsId
  @JsonBackReference
  @JoinColumn(name = "book_id", referencedColumnName = "id")
  private Book book;
  private Integer rating1;
  private Integer rating2;
  private Integer rating3;
  private Integer rating4;
  private Integer rating5;

  public Rating() {
    this.book = null;
    this.rating1 = 0;
    this.rating2 = 0;
    this.rating3 = 0;
    this.rating4 = 0;
    this.rating5 = 0;
  }

  public Integer getBookId() {
    return bookId;
  }

  public void setBookId(Integer bookId) {
    this.bookId = bookId;
  }

  public Book getBook() {
    return book;
  }

  public void setBook(Integer bookId) {
    this.book = book;
  }

  public Integer getRating1() {
    return rating1;
  }

  public void setRating1(Integer rating1) {
    this.rating1 = rating1;
  }

  public Integer getRating2() {
    return rating2;
  }

  public void setRating2(Integer rating2) {
    this.rating2 = rating2;
  }

  public Integer getRating3() {
    return rating3;
  }

  public void setRating3(Integer rating3) {
    this.rating3 = rating3;
  }

  public Integer getRating4() {
    return rating4;
  }

  public void setRating4(Integer rating4) {
    this.rating4 = rating4;
  }

  public Integer getRating5() {
    return rating5;
  }

  public void setRating5(Integer rating5) {
    this.rating5 = rating5;
  }

  public Integer getRatingAll() {
    return rating1 + rating2 + rating3 + rating4 + rating5;
  }

  public Integer getRatingAvg() {
    return
        Math.round
        (
            (5 * rating5 + 4 * rating4 + 3 * rating3 + 2 * rating2 + 1 * rating1)
            /
            (rating5 + rating4 + rating3 + rating2 + rating1)
        );
  }

  @Override
  public String toString() {
    return "rating{" +
           "bookId=" + bookId +
           ", rating1=" + rating1 +
           ", rating2=" + rating2 +
           ", rating3=" + rating3 +
           ", rating4=" + rating4 +
           ", rating5=" + rating5 +
           '}';
  }
}
