package com.example.MyBookShopApp.data;

import javax.persistence.*;

@Entity
@Table(name = "clients")
public class Client {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String firstName;
  private String lastName;
  private String login;
  private String password;

  @Override
  public String toString() {
    return "Client{" +
           "id=" + id +
           ", firstName='" + firstName + '\'' +
           ", lastName='" + lastName + '\'' +
           ", login='" + login + '\'' +
           ", password='" + password + '\'' +
           '}';
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

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
