package com.example.MyBookShopApp.controllers.user_related;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthorizationController {
  @GetMapping("/signin")
  public String getSignIn() {
    return "signin";
  }

  @GetMapping("/signup")
  public String getSignUp() {
    return "signup";
  }
}
