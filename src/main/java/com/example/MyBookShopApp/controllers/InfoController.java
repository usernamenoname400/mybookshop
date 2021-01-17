package com.example.MyBookShopApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InfoController {
  @GetMapping("/about")
  public String getAbout() {
    return "about";
  }

  @GetMapping("/faq")
  public String getFaq() {
    return "faq";
  }

  @GetMapping("/contacts")
  public String getContacts() {
    return "contacts";
  }

  @GetMapping("/documents")
  public String getDocuments() {
    return "documents/index";
  }

  @GetMapping("/documents/{documentId:\\d+}")
  public String getDocument() {
    return "documents/slug";
  }
}
