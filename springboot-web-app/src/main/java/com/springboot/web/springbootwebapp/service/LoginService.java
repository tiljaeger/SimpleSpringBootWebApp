package com.springboot.web.springbootwebapp.service;

import org.springframework.stereotype.Component;

@Component
public class LoginService {

  public boolean validateUser(String user, String password) {
    return user.equals("tjaeger") && password.equals("abc");
  }
}
