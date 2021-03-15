package com.example.MyBookShopApp.security.jwt;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface JWTBlacklistRepository extends JpaRepository<JWTTokenBlacklist, String> {

  List<JWTTokenBlacklist> findAllByExpiredBefore(Date expiredBefore);

}
