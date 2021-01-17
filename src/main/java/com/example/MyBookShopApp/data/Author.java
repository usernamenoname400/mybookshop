package com.example.MyBookShopApp.data;

public class Author {
  private Integer id;
  private String firstName;
  private String lastName;
  private String biographyShort;
  private String biographyRest;
  private String photo;

  public Author(Integer id, String firstName, String lastName, String biographyShort, String biographyRest,
                String photo) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.biographyShort = biographyShort;
    this.biographyRest = biographyRest;
    this.photo = photo;
  }

  public Author() {
  }

  @Override
  public String toString() {
    return "Author{" +
           "id=" + id +
           ", firstName='" + firstName + '\'' +
           ", lastName='" + lastName + '\'' +
           ", biographyShort='" + biographyShort + '\'' +
           ", biographyRest='" + biographyRest + '\'' +
           ", photo='" + photo + '\'' +
           '}';
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getFirsName() {
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
