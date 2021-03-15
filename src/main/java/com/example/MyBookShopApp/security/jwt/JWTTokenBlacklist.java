package com.example.MyBookShopApp.security.jwt;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "token_blacklist")
public class JWTTokenBlacklist {
  @Id
  @Column(name = "token")
  private String id;

  private Date expired;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Date getExpired() {
    return expired;
  }

  public void setExpired(Date expired) {
    this.expired = expired;
  }
}
