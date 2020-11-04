package com.springboot.web.springbootwebapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Controller("error")
public class ErrorController {
  
  // instead of using the Spring default error handling write your custom Error
  @ExceptionHandler(Exception.class)
  public ModelAndView handleException
    (HttpServletRequest request, Exception ex){
    // store here model and view details
    ModelAndView mv = new ModelAndView();

    mv.addObject("exception", ex.getLocalizedMessage());
    mv.addObject("url", request.getRequestURL());
    
    mv.setViewName("error");
    return mv;
  }

}