package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.SecurityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("app")
public class LoginController {

  private static final Logger logger = LogManager.getLogger(LoginController.class);
  @Autowired
  SecurityService securityService;
  @Autowired
  private UserRepository userRepository;

  @GetMapping("/login")
  public String login(Model model, String error, String logout) {

    if (securityService.isAuthenticated()) {
      logger.debug("user redirected to bidList/list");
      return "redirect:bidList/list";

    }
    if (error != null) {
      logger.debug("Error on login " + error);
      model.addAttribute("error", "Your username and password is invalid.");
    }
    if (logout != null) {
      logger.debug("User logged out " + logout);
      model.addAttribute("message", "You have been logged out successfully.");
    }
    return "home";
  }


  @GetMapping("secure/article-details")
  public ModelAndView getAllUserArticles() {
    ModelAndView mav = new ModelAndView();
    mav.addObject("users", userRepository.findAll());
    mav.setViewName("user/list");
    return mav;
  }

  @GetMapping("/error")
  public ModelAndView error() {
    ModelAndView mav = new ModelAndView();
    String errorMessage = "You are not authorized for the requested data.";
    mav.addObject("errorMsg", errorMessage);
    mav.setViewName("403");
    return mav;
  }
}
