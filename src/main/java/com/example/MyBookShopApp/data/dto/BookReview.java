package com.example.MyBookShopApp.data.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="book_review")
@ApiModel(description = "Entity representing review for a book")
public class BookReview {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_review_generator")
  @SequenceGenerator(name="book_review_generator", sequenceName = "book_review_id_seq", allocationSize=1)
  @ApiModelProperty("Identity, generated db automatically")
  private Integer id;
  @ManyToOne
  @JoinColumn(name = "book_id", referencedColumnName = "id")
  private Book book;
  @Column(name = "user_id")
  private Integer userId;
  @Column(name = "session_id")
  private String sessionId;
  @Column(name = "user_name")
  private String userName;
  private Date time;
  private String text;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    this.book = book;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
