package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;


@Controller
public class BidListController {

  private static final Logger logger = LoggerFactory.getLogger(BidListController.class);
  @Autowired
  BidListService bidListService;

  @RequestMapping("/bidList/list")
  public String home(Model model) {

    model.addAttribute("bidList", bidListService.findAll());
    logger.debug("bidList displayed");
    return "bidList/list";
  }

  @GetMapping("/bidList/add")
  public String addBidForm(BidList bid) {
    logger.debug("bid add displayed");
    return "bidList/add";
  }

  @PostMapping("/bidList/validate")
  public String validate(@Valid BidList bid, BindingResult result, Model model) {
    // TODO: check data valid and save to db, after saving return bid list
    if (!result.hasErrors()) {
      bid.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
      bidListService.save(bid);
      logger.debug("bid validated successfully");
      return "redirect:/bidList/list";
    }
    logger.debug("bid not validated: " + result.getAllErrors().toString());
    return "redirect:bidList/add";
  }

  @GetMapping("/bidList/update/{id}")
  public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    // TODO: get Bid by Id and to model then show to the form
    BidList bid =
            bidListService.findById(id).orElseThrow(() -> new IllegalArgumentException(
                    "Invalid BidList id: " + id));

    model.addAttribute("bidList", bid);
    logger.debug("bid update form sent for id: " + id);
    return "bidList/update";

  }

  @PostMapping("/bidList/update/{id}")
  public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                          BindingResult result, Model model) {
    // TODO: check required fields, if valid call service to update Bid and return list Bid

    if (!result.hasErrors() && bidListService.findById(id).isPresent()) {
      bidList.setId(id);
      bidList.setRevisionDate(Timestamp.valueOf(LocalDateTime.now()));
      bidListService.save(bidList);
      logger.debug("bid for id: " + id);
      return "redirect:/bidList/list";
    }
    logger.debug("bid not updated for id: " + id + " " + result.getAllErrors().toString());
    return "/bidList/list";
  }

  @GetMapping("/bidList/delete/{id}")
  public String deleteBid(@PathVariable("id") Integer id, Model model) {
    // TODO: Find Bid by Id and delete the bid, return to Bid list
    BidList bid =
            bidListService.findById(id).orElseThrow(() -> new IllegalArgumentException(
                    "Invalid BidList id: " + id));
    bidListService.delete(bid);
    logger.debug("bid deleted for id: " + id);
    return "redirect:/bidList/list";
  }
}
