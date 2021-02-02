package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TagController {
  private final TagService tagService;

  @Autowired
  public TagController(TagService tagService) {
    this.tagService = tagService;
  }

  @GetMapping("/tags/{tagId:\\d+}")
  public String getTag(@PathVariable Integer tagId, Model model) {
    model.addAttribute("tagBooks", tagService.getBooksByTag(tagId));
    model.addAttribute("tag", tagService.getTag(tagId));
    return "tags/index";
  }
}
