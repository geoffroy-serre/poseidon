package com.nnk.springboot.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {
  private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

  @RequestMapping("/")
  public String home(Model model) {
    logger.debug("User redirected to home");
    return "home";
  }

  @RequestMapping("/admin/home")
  public String adminHome(Model model) {
    return "redirect:/bidList/list";
  }


}
