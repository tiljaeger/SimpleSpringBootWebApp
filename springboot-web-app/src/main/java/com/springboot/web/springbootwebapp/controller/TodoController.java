package com.springboot.web.springbootwebapp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.springboot.web.springbootwebapp.model.Todo;
import com.springboot.web.springbootwebapp.service.TodoService;

@Controller
@SessionAttributes("name")
// session attributes in model are available across different requests and controllers
public class TodoController {

  @Autowired
  TodoService service;

  // for date formatiing, map String to a java.util.Date
  @InitBinder
  public void initBinder(WebDataBinder binder) {
    // Dateformat -> dd/MM/yyyy
    // use this date formatl always for Date class
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
  }

  @RequestMapping(value = "/list-todos", method = RequestMethod.GET)
  public String showTodosList(ModelMap model) {

    // get model session attribute name
    String name = getLoggedInUserName(model);
    model.put("todos", service.retrieveTodos(name));
    return "list-todos";
  }

  public String getLoggedInUserName(ModelMap model) {
    return (String) model.get("name");
  }

  @RequestMapping(value = "/add-todo", method = RequestMethod.GET)
  public String showAddTodoPage(ModelMap model) {
    // default object is bound when we create command bean
    model.addAttribute("todo", new Todo(0, getLoggedInUserName(model), "Default Desc", new Date(), false));
    // redirect to add-todo page
    return "todo";
  }

  @RequestMapping(value = "/delete-todo", method = RequestMethod.GET)
  public String deleteTodo(@RequestParam int id) {
    service.deleteTodo(id);
    return "redirect:/list-todos";
  }

  @RequestMapping(value = "/update-todo", method = RequestMethod.GET)
  public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
    Todo todo = service.retrieveTodo(id);
    model.put("todo", todo);
    return "todo";
  }

  @RequestMapping(value = "/update-todo", method = RequestMethod.POST)
  public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {

    if (result.hasErrors()) {
      return "todo";
    }

    todo.setUser(getLoggedInUserName(model));

    service.updateTodo(todo);

    return "redirect:/list-todos";
  }

  @RequestMapping(value = "/add-todo", method = RequestMethod.POST)
  // public String addTodo(ModelMap model, @RequestParam String desc) {
  // use Command Bean Todo
  // ise @Valid validation described in todo bean
  // result of validation is written in BindungResult
  public String addTodo(ModelMap model, @Valid Todo todo, BindingResult result) {

    if (result.hasErrors()) {
      return "todo";
    }
    // redirect to add-todo page
    // name is already in session
    service.addTodo(getLoggedInUserName(model), todo.getDesc(), todo.getTargetDate(), false);
    return "redirect:/list-todos";
  }

}
