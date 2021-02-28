package com.example.MyBookShopApp.data.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name="book")
@ApiModel(description = "Entity representing a book")
public class Book extends RepresentationModel<Book> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ApiModelProperty("Identity, generated db automatically")
  private Integer id;
  @Column(name = "pub_date")
  @ApiModelProperty("Book's publication date")
  private Date pubDate;
  @ManyToOne
  @JoinColumn(name = "author_id", referencedColumnName = "id")
  private Author author;
  @Column(name = "is_bestseller")
  @ApiModelProperty("Value 1 representing a bestseller book, else book is regular, not bestseller")
  private Integer isBestseller;
  @ApiModelProperty("Mnemonical identity")
  private String slug;
  @ApiModelProperty("Image url")
  private String image;
  @OneToMany(mappedBy = "book")
  private List<BookFile> bookFileList = new ArrayList<>();
  @ApiModelProperty("Book title")
  private String title;
  @ApiModelProperty("Regular price (without discount)")
  private Integer price;
  @ApiModelProperty("Discount in percent min 0%, max 100%")
  private Integer discount;
  @Column(columnDefinition = "TEXT")
  @ApiModelProperty("Book description")
  private String description;
  @ManyToOne
  @JsonManagedReference
  @JoinColumn(name = "genre_id", referencedColumnName = "id")
  private Genre genre;
  @OneToOne(mappedBy = "book")
  @PrimaryKeyJoinColumn
  @JsonIgnore
  private Rating rating;
  @ManyToMany(mappedBy = "bookList")
  @JsonIgnore
  private List<Tag> tagList = new ArrayList<>();
  @OneToMany(mappedBy = "book")
  @JsonIgnore
  private List<BookReview> bookReviewList = new ArrayList<>();

  @Override
  public String toString() {
    return "Book{" +
           "id=" + id +
           ", author=" + author +
           ", title='" + title + '\'' +
           ", discount='" + discount + '\'' +
           ", price='" + price + '\'' +
           ", pubDate=" + pubDate +
           ", rating=" + rating +
           ", genres=" + genre +
           '}';
  }

  @JsonGetter("discountPrice")
  public Integer discountedPrice() {
    Integer newPrice = price - Math.toIntExact(Math.round(price * discount / 100));
    return newPrice;
  }

  @JsonGetter("authors")
  public String authorFullName() {
    return author.getFirstName() + ' ' + author.getLastName();
  }

  @JsonGetter("authorId")
  public Integer authorId() {
    return author.getId();
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

  public Integer getDiscount() {
    return discount;
  }

  public void setDiscount(Integer discount) {
    this.discount = discount;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public Rating getRating() {
    return rating;
  }

  public void setRating(Rating rating) {
    this.rating = rating;
  }

  public Genre getGenre() {
    return genre;
  }

  public void setGenre(Genre genres) {
    this.genre = genres;
  }

  public List<Tag> getTagList() {
    return tagList;
  }

  public void setTagList(List<Tag> tagList) {
    this.tagList = tagList;
  }

  public Date getPubDate() {
    return pubDate;
  }

  public void setPubDate(Date pubDate) {
    this.pubDate = pubDate;
  }

  public Integer getIsBestseller() {
    return isBestseller;
  }

  public void setIsBestseller(Integer isBestseller) {
    this.isBestseller = isBestseller;
  }

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<BookFile> getBookFileList() {
    return bookFileList;
  }

  public void setBookFileList(List<BookFile> bookFileList) {
    this.bookFileList = bookFileList;
  }

  public List<BookReview> getBookReviewList() {
    if (bookReviewList != null && bookReviewList.size() > 1) {
      bookReviewList = bookReviewList.stream().
          sorted(Comparator.comparing(BookReview::getTime).reversed()).
          collect(Collectors.toList());
    }
    return bookReviewList;
  }

  public void setBookReviewList(List<BookReview> bookReviewList) {
    this.bookReviewList = bookReviewList;
  }
}
