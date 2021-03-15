package com.example.MyBookShopApp.security.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;

@Service
public class JWTUtil {
  @Value("${auth.secret}")
  private String secret;

  private String createToken(Map<String, Object> claims, String userName) {
    return Jwts
              .builder()
              .setClaims(claims)
              .setSubject(userName)
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
              .signWith(SignatureAlgorithm.HS256, secret)
              .compact();
  }

  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, userDetails.getUsername());
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
    Claims claims = extractAllClaims(token);
    if (claims != null) {
      return claimResolver.apply(claims);
    } else {
      return null;
    }
  }

  private Claims extractAllClaims(String token) {
    Claims result = null;

    try {
      result = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    } catch (JwtException eSign) {
      Logger.getLogger(this.getClass().getSimpleName()).warning("JWT token parse exception : " + eSign.getMessage());
    }

    return result;
  }

  public String extractUserName(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    String userName = extractUserName(token);
    return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
}
