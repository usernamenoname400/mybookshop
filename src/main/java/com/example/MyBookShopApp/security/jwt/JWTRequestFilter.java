package com.example.MyBookShopApp.security.jwt;

import com.example.MyBookShopApp.security.BookstoreUserDetailService;
import com.example.MyBookShopApp.security.BookstoreUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {
  private final BookstoreUserDetailService bookstoreUserDetailService;
  private final JWTUtil jwtUtil;
  private final JWTBlacklistService blacklistService;

  @Autowired
  public JWTRequestFilter(
      BookstoreUserDetailService bookstoreUserDetailService,
      JWTUtil jwtUtil,
      JWTBlacklistService blacklistService
  )
  {
    this.bookstoreUserDetailService = bookstoreUserDetailService;
    this.jwtUtil = jwtUtil;
    this.blacklistService = blacklistService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      FilterChain filterChain
  ) throws ServletException, IOException
  {
    String token = null;
    String userName = null;
    Cookie tokenCookie = null;
    Cookie[] cookies = httpServletRequest.getCookies();

    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("token")) {
          tokenCookie = cookie;
          token = tokenCookie.getValue();
          userName = jwtUtil.extractUserName(token);
          break;
        }
      }
      if (token != null && !token.isEmpty() && blacklistService.isBlackListed(token)) {
        Logger.getLogger(this.getClass().getSimpleName()).warning("Blacklisted token for " + userName);
        tokenCookie.setMaxAge(0);
        httpServletResponse.addCookie(tokenCookie);
        SecurityContextHolder.clearContext();
      } else if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        BookstoreUserDetails userDetails = (BookstoreUserDetails) bookstoreUserDetailService.loadUserByUsername(userName);
        if (jwtUtil.validateToken(token, userDetails)) {
          UsernamePasswordAuthenticationToken authenticationToken =
              new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
          authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
      } else if (userName == null && tokenCookie != null) {
        tokenCookie.setMaxAge(0);
        httpServletResponse.addCookie(tokenCookie);
        SecurityContextHolder.clearContext();
      }
    }
    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }
}
