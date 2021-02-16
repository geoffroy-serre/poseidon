package com.nnk.springboot.controllers;


import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RatingController {
  private static final Logger logger = LoggerFactory.getLogger(RatingController.class);

  @Autowired
  RatingService ratingService;

  @RequestMapping("/rating/list")
  public String home(Model model) {

    model.addAttribute("ratings", ratingService.findAll());
    logger.debug("rating list displayed");
    return "rating/list";
  }

  @GetMapping("/rating/add")
  public String addRatingForm(Rating rating) {
    logger.debug("rating add displayed");
    return "rating/add";
  }

  @PostMapping("/rating/validate")
  @Transactional
  public String validate(@Valid Rating rating, BindingResult result, Model model) {
    if (!result.hasErrors()) {
      ratingService.save(rating);
      logger.debug("rate validated");
      return "redirect:/rating/list";
    }
    logger.debug("rating not validated " + result.getAllErrors().toString());
    return "rating/list";
  }

  @GetMapping("/rating/update/{id}")
  public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

    Rating rating =
            ratingService.findById(id).orElseThrow(() -> new IllegalArgumentException(
                    "Invalid Rating id: " + id));

    model.addAttribute("rating", rating);
    logger.debug("rating update form displayed for id: " + id);
    return "rating/update";
  }

  @PostMapping("/rating/update/{id}")
  @Transactional
  public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {

    if (!result.hasErrors() && ratingService.findById(id).isPresent()) {
      rating.setId(id);
      ratingService.save(rating);
      logger.debug("rating updated for id: " + id);
      return "redirect:/rating/list";
    }
    logger.debug("rating not updated for id: " + id + " " + result.getAllErrors().toString());
    return "rating/list";
  }

  @GetMapping("/rating/delete/{id}")
  @Transactional
  public String deleteRating(@PathVariable("id") Integer id, Model model) {
    Rating rating =
            ratingService.findById(id).orElseThrow(() -> new IllegalArgumentException(
                    "Invalid Rating id: " + id));
    ratingService.delete(rating);
    logger.debug("rating deleted for id: " + id);
    return "redirect:/rating/list";
  }
}
