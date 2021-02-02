package com.example.MyBookShopApp.data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="books")
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @ManyToOne
  @JoinColumn(name = "author_id", referencedColumnName = "id")
  private Author author;
  @ManyToOne
  @JoinColumn(name = "genres_id", referencedColumnName = "id")
  private Genres genres;
  private String title;
  private String priceOld;
  private String price;
  private String descriptionShort;
  private String descriptionRest;
  private Date dtRelease;
  @OneToOne(mappedBy = "book")
  @PrimaryKeyJoinColumn
  private Rating rating;
  @ManyToMany(mappedBy = "bookList")
  private List<Tag> tagList = new ArrayList<>();

  @Override
  public String toString() {
    return "Book{" +
           "id=" + id +
           ", author=" + author +
           ", title='" + title + '\'' +
           ", priceOld='" + priceOld + '\'' +
           ", price='" + price + '\'' +
           ", dtRelease=" + dtRelease +
           ", rating=" + rating +
           ", genres=" + genres +
           '}';
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Author getAuthor() {
    return author;
  }

  public void setAuthor(Author author) {
    this.author = author;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPriceOld() {
    return priceOld;
  }

  public void setPriceOld(String priceOld) {
    this.priceOld = priceOld;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public Rating getRating() {
    return rating;
  }

  public void setRating(Rating rating) {
    this.rating = rating;
  }

  public Date getDtRelease() {
    return dtRelease;
  }

  public void setDtRelease(Date dtRelease) {
    this.dtRelease = dtRelease;
  }

  public Genres getGenres() {
    return genres;
  }

  public void setGenres(Genres genres) {
    this.genres = genres;
  }

  public String getDescriptionShort() {
    return descriptionShort;
  }

  public void setDescriptionShort(String descriptionShort) {
    this.descriptionShort = descriptionShort;
  }

  public String getDescriptionRest() {
    return descriptionRest;
  }

  public void setDescriptionRest(String descriptionRest) {
    this.descriptionRest = descriptionRest;
  }

  public List<Tag> getTagList() {
    return tagList;
  }

  public void setTagList(List<Tag> tagList) {
    this.tagList = tagList;
  }
}
