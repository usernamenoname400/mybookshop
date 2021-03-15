package com.example.MyBookShopApp.security;

import com.example.MyBookShopApp.security.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Random;
import java.util.logging.Logger;

@Service
public class BookStoreUserRegister {
  private final BookstoreUserRepository bookstoreUserRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final BookstoreUserDetailService bookstoreUserDetailService;
  private final JWTUtil jwtUtil;

  @Autowired
  public BookStoreUserRegister(
      BookstoreUserRepository bookstoreUserRepository,
      PasswordEncoder passwordEncoder,
      AuthenticationManager authenticationManager,
      BookstoreUserDetailService bookstoreUserDetailService,
      JWTUtil jwtUtil
  ) {
    this.bookstoreUserRepository = bookstoreUserRepository;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.bookstoreUserDetailService = bookstoreUserDetailService;
    this.jwtUtil = jwtUtil;
  }

  public void registerNewUser(RegistrationForm registrationForm) {
    if (bookstoreUserRepository.findBookstoreUserByEmail(registrationForm.getEmail()) == null) {
      BookstoreUser user = new BookstoreUser();
      user.setName(registrationForm.getName());
      user.setEmail(registrationForm.getEmail());
      user.setPhone(registrationForm.getPhone());
      user.setPassword(passwordEncoder.encode(registrationForm.getPass()));
      bookstoreUserRepository.save(user);
    }

  }

  public ContactConfirmationResponse login(ContactConfirmationPayload payload) {
    Authentication authentication =
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(), payload.getCode()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    ContactConfirmationResponse response = new ContactConfirmationResponse();
    response.setResult("true");
    return response;
  }

  public ContactConfirmationResponse jwtLogin(ContactConfirmationPayload payload) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(), payload.getCode()));
    BookstoreUserDetails userDetails = (BookstoreUserDetails) bookstoreUserDetailService.loadUserByUsername(payload.getContact());
    String jwtToken = jwtUtil.generateToken(userDetails);
    ContactConfirmationResponse response = new ContactConfirmationResponse();
    response.setResult(jwtToken);
    return response;
  }

  public BookstoreUser getCurrentUser() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (principal == null) {
      return null;
    }

    if (principal instanceof DefaultOAuth2User) {
      DefaultOAuth2User user = (DefaultOAuth2User)principal;

      if (user.getAttributes() != null && !user.getAttributes().isEmpty() && user.getAttributes().containsKey("email")) {
        for (String key : user.getAttributes().keySet()) {
          Logger.getLogger(this.getClass().getSimpleName()).info("\"" + key + "\" = \"" + user.getAttributes().get(key) + "\"");
        }
        String email = (String)user.getAttributes().get("email");
        BookstoreUser bookstoreUser = bookstoreUserRepository.findBookstoreUserByEmail(email);
        if (bookstoreUser != null) {
          return bookstoreUser;
        } else {
          bookstoreUser = new BookstoreUser();
          bookstoreUser.setEmail((String)user.getAttributes().get("email"));
          bookstoreUser.setName((String)user.getAttributes().get("name"));
          byte[] passwordByte = new byte[7];
          new Random().nextBytes(passwordByte);
          bookstoreUser.setPassword(new String(passwordByte, Charset.forName("Windows-1251")));
          bookstoreUserRepository.save(bookstoreUser);
          return bookstoreUser;
        }
      } else {
        return null;
      }
    }

    if (principal instanceof BookstoreUserDetails) {
      return ((BookstoreUserDetails) principal).getBookstoreUser();
    }

    return null;
  }
}
