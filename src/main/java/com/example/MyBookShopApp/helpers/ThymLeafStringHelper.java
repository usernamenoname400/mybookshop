package com.example.MyBookShopApp.helpers;

public class ThymLeafStringHelper {
  public String getLeadingPart(String text, int maxLength) {
    if (text == null) {
      return "";
    }
    if (text.length() <= maxLength) {
      return text;
    }
    int i = maxLength;
    while (i > 0 && text.charAt(i) != ' ') {
      i--;
    }

    return text.substring(0, i - 1);
  }

  public String getSuffixPart(String text, int startPos) {
    if (text == null) {
      return "";
    }
    if (text.length() <= startPos) {
      return "";
    }
    int i = startPos;
    while (i > 0 && text.charAt(i) != ' ') {
      i--;
    }

    return text.substring(i + 1);
  }
}
