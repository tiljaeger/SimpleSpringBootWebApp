package com.springboot.web.springbootwebapp.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("name")
// session attributes in model are available across different requests and controllers
public class WelcomeController {

//  @Autowired
//  LoginService service;

  // use String in return
  // @RequestMapping(value="/login")
  // @ResponseBody
  // public String loginMessage() {
  // return "Hello";
  // }

  // use login.jsp via ViewResolver (/login -> /src/main/webapp/WEB-INF/jsp/login.jsp)
  // model is udes to pass data from controller to view (jsp) --> MVC
  // @RequestMapping(value = "/login") // used for GET, POST, PUT, ...
  // public String loginMessage(@RequestParam String name, ModelMap model) {
  // model.put("name", name);
  // return "login";
  // }

  // use a login form for login data, only used for GET
  // Security: if any url -> send the user to login page
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String showWelcomePage(ModelMap model) {
    model.put("name", getLoggedInUserName());
    return "welcome";
  }

  // get information about the logged in user by Spring Security
  private String getLoggedInUserName() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
      return ((UserDetails) principal).getUsername();
    }
    return principal.toString();
  }

}
