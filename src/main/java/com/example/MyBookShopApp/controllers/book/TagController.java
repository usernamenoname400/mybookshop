package com.example.MyBookShopApp.controllers.book;

import com.example.MyBookShopApp.data.dto.BooksPageDTO;
import com.example.MyBookShopApp.data.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TagController {
  private final TagService tagService;
  @Value("${sql.rowlimit}")
  private int rowLimit;

  @Autowired
  public TagController(TagService tagService) {
    this.tagService = tagService;
  }

  @GetMapping("/tags/{tagId:\\d+}")
  public String getTag(@PathVariable Integer tagId, Model model) {
    model.addAttribute("tagBooks", tagService.getBooksByTag(tagId, 0, rowLimit).getContent());
    model.addAttribute("tag", tagService.getTag(tagId));
    return "tags/index";
  }

  @GetMapping("/tags/page/{tagId:\\d+}")
  @ResponseBody
  public BooksPageDTO getNextSearchPage(
      @PathVariable Integer tagId,
      @RequestParam("offset") Integer offset,
      @RequestParam("limit") Integer limit
  ) {
    return new BooksPageDTO(tagService.getBooksByTag(tagId, offset, limit).getContent());
  }
}
