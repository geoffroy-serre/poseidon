package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;


@Controller
public class UserController {
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);
  private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,60}$";
  private static final Pattern PASSWORD = Pattern.compile(PASSWORD_REGEX);
  @Autowired
  private UserService userService;

  @RequestMapping("/user/list")
  public String home(Model model) {
    model.addAttribute("users", userService.findAll());
    logger.debug("User list displayed");
    return "user/list";
  }

  @GetMapping("/user/add")
  public String addUser(User bid) {
    logger.debug("User add form displayed ");
    return "user/add";
  }

  @PostMapping("/user/validate")
  @Transactional
  public String validate(@Valid User user, BindingResult result, Model model) {
    if (!result.hasErrors() && PASSWORD.matcher(user.getPassword()).matches()) {
      BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
      user.setPassword(encoder.encode(user.getPassword()));
      userService.save(user);
      //model.addAttribute("users", userService.findAll());
      logger.debug("User validated ");
      return "redirect:/user/list";
    }
    logger.debug("User not validated " + result.getAllErrors().toString());
    return "redirect:/user/add";
  }

  @GetMapping("/user/update/{id}")
  public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException(
            "Invalid user Id " + id));
    user.setPassword("");
    model.addAttribute("user", user);
    logger.debug("User update form displayd for id: " + id);
    return "user/update";
  }

  @PostMapping("/user/update/{id}")
  @Transactional
  public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                           BindingResult result, Model model) {
    userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id " + id));
    if (result.hasErrors() || !PASSWORD.matcher(user.getPassword()).matches()) {
      logger.debug("User not updated id: " + id + " " + result.getAllErrors().toString());
      return "redirect:user/update";
    }


    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    user.setPassword(encoder.encode(user.getPassword()));
    user.setId(id);
    userService.save(user);
    model.addAttribute("users", userService.findAll());
    logger.debug("User updated id: " + id + " " + result.getAllErrors().toString());
    return "redirect:/user/list";
  }

  @GetMapping("/user/delete/{id}")
  @Transactional
  public String deleteUser(@PathVariable("id") Integer id, Model model) {
    userService.deleteById(id);
    model.addAttribute("users", userService.findAll());
    logger.debug("User deleted id: " + id);
    return "redirect:/user/list";
  }
}
