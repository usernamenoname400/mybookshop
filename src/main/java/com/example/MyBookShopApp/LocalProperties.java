package com.example.MyBookShopApp;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="local")
public class LocalProperties {
  private String basePath;
  private String mainPage;
  private String genresPage;
  private String authorsPage;
  private String imagesPath;
  private String placeholderPath;

  public String getBasePath() {
    return basePath;
  }

  public void setBasePath(String basePath) {
    this.basePath = basePath;
  }

  public String getMainPage() {
    return mainPage;
  }

  public void setMainPage(String mainPage) {
    this.mainPage = mainPage;
  }

  public String getGenresPage() {
    return genresPage;
  }

  public void setGenresPage(String genresPage) {
    this.genresPage = genresPage;
  }

  public String getAuthorsPage() {
    return authorsPage;
  }

  public void setAuthorsPage(String authorsPage) {
    this.authorsPage = authorsPage;
  }

  public String getImagesPath() {
    return imagesPath;
  }

  public void setImagesPath(String imagesPath) {
    this.imagesPath = imagesPath.replace('.', '/');
  }

  public String getPlaceholderPath() {
    return placeholderPath;
  }

  public void setPlaceholderPath(String placeholderPath) {
    this.placeholderPath = placeholderPath.replace('.', '/');
  }

  public String getMainPageFullPath() {
    String result;
    if (basePath.startsWith("/")) {
      result = basePath;
    } else {
      result = '/' + basePath;
    }

    if (mainPage.startsWith("/")) {
      result = result + mainPage;
    } else {
      result = result + '/' + mainPage;
    }

    return result;
  }

  public String getGenresPageFullPath() {
    String result;
    if (basePath.charAt(0) == '/') {
      result = basePath;
    } else {
      result = '/' + basePath;
    }

    if (genresPage.startsWith("/")) {
      result = result + genresPage;
    } else {
      result = result + '/' + genresPage;
    }

    return result;
  }

  public String getAuthorsPageFullPath() {
    String result;
    if (basePath.startsWith("/")) {
      result = basePath;
    } else {
      result = '/' + basePath;
    }

    if (authorsPage.startsWith("/")) {
      result = result + authorsPage;
    } else {
      result = result + '/' + authorsPage;
    }

    return result;
  }
}
