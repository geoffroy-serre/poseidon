package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;


@Controller
public class UserController {
  @Autowired
  private UserService userService;
  Logger logger = LogManager.getLogger(getClass().getName());

  private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,60}$";
  private static final Pattern PASSWORD = Pattern.compile(PASSWORD_REGEX);

  @RequestMapping("/user/list")
  public String home(Model model) {
    model.addAttribute("users", userService.findAll());
    return "user/list";
  }

  @GetMapping("/user/add")
  public String addUser(User bid) {
    return "user/add";
  }

  @PostMapping("/user/validate")
  public String validate(@Valid User user, BindingResult result, Model model) {
    if (!result.hasErrors() && PASSWORD.matcher(user.getPassword()).matches()) {
      BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
      user.setPassword(encoder.encode(user.getPassword()));
      userService.save(user);
      model.addAttribute("users", userService.findAll());
      return "redirect:/user/list";
    }
    System.out.println(result.getAllErrors());
    return "redirect:/user/add";
  }

  @GetMapping("/user/update/{id}")
  public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException(
            "Invalid user Id " + id));
    user.setPassword("");
    model.addAttribute("user", user);
    return "user/update";
  }

  @PostMapping("/user/update/{id}")
  public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                           BindingResult result, Model model) {
    userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id " + id));
    if (result.hasErrors() || !PASSWORD.matcher(user.getPassword()).matches()) {
       return "user/update";
    }
    System.out.println(user.toString());

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    user.setPassword(encoder.encode(user.getPassword()));
    user.setId(id);
    userService.save(user);
    model.addAttribute("users", userService.findAll());
    return "redirect:/user/list";
  }

  @GetMapping("/user/delete/{id}")
  public String deleteUser(@PathVariable("id") Integer id, Model model) {
    userService.deleteById(id);
    model.addAttribute("users", userService.findAll());
    return "redirect:/user/list";
  }
}
