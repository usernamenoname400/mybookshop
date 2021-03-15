package com.example.MyBookShopApp.security.jwt;

import org.springframework.stereotype.Service;

@Service
public class JWTBlacklistService {
  private final JWTBlacklistRepository repository;

  public JWTBlacklistService(JWTBlacklistRepository repository) {
    this.repository = repository;
  }

  public Boolean isBlackListed(String token) {
    return repository.existsById(token);
  }
}
