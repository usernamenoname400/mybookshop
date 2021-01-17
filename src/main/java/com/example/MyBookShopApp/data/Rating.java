package com.example.MyBookShopApp.data;

public class Rating {
  private int bookId;
  private int rating1;
  private int rating2;
  private int rating3;
  private int rating4;
  private int rating5;

  public Rating(int bookId, int rating1, int rating2, int rating3, int rating4, int rating5) {
    this.bookId = bookId;
    this.rating1 = rating1;
    this.rating2 = rating2;
    this.rating3 = rating3;
    this.rating4 = rating4;
    this.rating5 = rating5;
  }

  public int getBookId() {
    return bookId;
  }

  public void setBookId(int bookId) {
    this.bookId = bookId;
  }

  public int getRating1() {
    return rating1;
  }

  public void setRating1(int rating1) {
    this.rating1 = rating1;
  }

  public int getRating2() {
    return rating2;
  }

  public void setRating2(int rating2) {
    this.rating2 = rating2;
  }

  public int getRating3() {
    return rating3;
  }

  public void setRating3(int rating3) {
    this.rating3 = rating3;
  }

  public int getRating4() {
    return rating4;
  }

  public void setRating4(int rating4) {
    this.rating4 = rating4;
  }

  public int getRating5() {
    return rating5;
  }

  public void setRating5(int rating5) {
    this.rating5 = rating5;
  }

  public int getRatingAll() {
    return rating1 + rating2 + rating3 + rating4 + rating5;
  }

  public int getRatingAvg() {
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
