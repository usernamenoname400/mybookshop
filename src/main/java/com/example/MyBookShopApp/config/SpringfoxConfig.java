package com.example.MyBookShopApp.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@Configuration
public class SpringfoxConfig {
  @Bean
  public Docket docket() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.example.MyBookShopApp.controllers"))
        .paths(PathSelectors.regex("^/api/.+"))
        //.paths(PathSelectors.any())
        .build()
        .apiInfo(apiInfo());
  }

  public ApiInfo apiInfo() {
    return new ApiInfo(
        "Bookshop api",
        "Api for bookstore",
        "1.0",
        "http://notfound.info",
        new Contact("Api owner", "http://owner.api.info", "owner@api.info"),
        "api_license",
        "http://license.edu.org",
        new ArrayList<>()
    );
  }
}
