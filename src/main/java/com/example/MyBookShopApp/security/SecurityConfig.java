package com.example.MyBookShopApp.security;

import com.example.MyBookShopApp.security.jwt.JWTBlacklistRepository;
import com.example.MyBookShopApp.security.jwt.JWTLogoutHandler;
import com.example.MyBookShopApp.security.jwt.JWTRequestFilter;
import com.example.MyBookShopApp.security.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
  private final BookstoreUserDetailService bookstoreUserDetailService;
  private final JWTRequestFilter filter;
  private final JWTBlacklistRepository blacklistRepository;
  private final JWTUtil util;

  @Autowired
  public SecurityConfig(
      BookstoreUserDetailService bookstoreUserDetailService,
      JWTRequestFilter filter,
      JWTBlacklistRepository blacklistRepository,
      JWTUtil util
  ) {
    this.bookstoreUserDetailService = bookstoreUserDetailService;
    this.filter = filter;
    this.blacklistRepository = blacklistRepository;
    this.util = util;
  }

  @Bean
  PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Override
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .userDetailsService(bookstoreUserDetailService)
        .passwordEncoder(getPasswordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/my", "/profile", "/books/**/addReview").authenticated()//.hasRole("USER")
        .antMatchers("/**").permitAll()
        .and().formLogin().loginPage("/signin").failureUrl("/signin")
        .and().logout().logoutUrl("/logout")
        .logoutSuccessHandler(new JWTLogoutHandler(blacklistRepository, util))
        .logoutSuccessUrl("/signin").deleteCookies("token")
        .and().oauth2Login()
        .and().oauth2Client();

    //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandlerImpl());
  }
}
