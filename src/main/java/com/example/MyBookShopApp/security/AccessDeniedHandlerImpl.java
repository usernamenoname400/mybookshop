package com.example.MyBookShopApp.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
  @Override
  public void handle(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      AccessDeniedException exception
  ) throws IOException, ServletException
  {
    Logger.getLogger(this.getClass().getSimpleName()).warning("requested acess denied page " + httpServletRequest.getRequestURI());
    httpServletResponse.sendRedirect("/signin");
  }
}
