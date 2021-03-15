package com.example.MyBookShopApp.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class JWTLogoutHandler implements LogoutSuccessHandler {
  private final JWTBlacklistRepository repository;
  private final JWTUtil util;

  public JWTLogoutHandler(JWTBlacklistRepository repository, JWTUtil util) {
    this.repository = repository;
    this.util = util;
  }

  @Override
  public void onLogoutSuccess(
      HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication
  ) throws IOException, ServletException
  {
    String token = null;
    for (Cookie cookie : request.getCookies()) {
      if (cookie.getName().equals("token")) {
        token = cookie.getValue();
      }
    }
    if (token != null && !repository.existsById(token)) {
      JWTTokenBlacklist blacklistToken = new JWTTokenBlacklist();
      blacklistToken.setId(token);
      blacklistToken.setExpired(util.extractExpiration(token));
      repository.save(blacklistToken);
    }

    List<JWTTokenBlacklist> tokens = repository.findAllByExpiredBefore(new Date());
    for (JWTTokenBlacklist tokenToDelete : tokens) {
      repository.delete(tokenToDelete);
    }

    response.sendRedirect("/");
  }
}
