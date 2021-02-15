package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
public class TradeController {
  private static final Logger logger = LoggerFactory.getLogger(TradeController.class);

  @Autowired
  TradeService tradeService;

  @RequestMapping("/trade/list")
  public String home(Model model) {

    model.addAttribute("trades", tradeService.findAll());
    logger.debug("trade list displayed");
    return "trade/list";
  }

  @GetMapping("/trade/add")
  public String addUser(Trade trade) {
    logger.debug("trade add form displayed");
    return "trade/add";
  }

  @PostMapping("/trade/validate")
  @Transactional
  public String validate(@Valid Trade trade, BindingResult result, Model model) {

    if (!result.hasErrors()) {
      trade.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
      tradeService.save(trade);
      logger.debug("trade validated");
      return "redirect:/trade/list";
    }
    logger.debug("trade not validated " + result.getAllErrors().toString());
    return "redirect:/trade/add";
  }

  @GetMapping("/trade/update/{id}")
   public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

    Trade trade =
            tradeService.findById(id).orElseThrow(() -> new IllegalArgumentException(
                    "Invalid Trade id: " + id));

    model.addAttribute("trade", trade);
    logger.debug("trade update form sent for id: " + id);
    return "trade/update";
  }

  @PostMapping("/trade/update/{id}")
  @Transactional
  public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                            BindingResult result, Model model) {

    if (!result.hasErrors() && tradeService.findById(id).isPresent()) {
      trade.setId(id);
      trade.setRevisionDate(Timestamp.valueOf(LocalDateTime.now()));
      tradeService.save(trade);
      logger.debug("trade updated for id: " + id);
      return "redirect:/trade/list";
    }
    logger.debug("trade not updated id: " + id + " " + result.getAllErrors().toString());
    return "redirect:/trade/list";
  }

  @GetMapping("/trade/delete/{id}")
  @Transactional
  public String deleteTrade(@PathVariable("id") Integer id, Model model) {
    Trade trade =
            tradeService.findById(id).orElseThrow(() -> new IllegalArgumentException(
                    "Invalid Trade id: " + id));
    tradeService.delete(trade);
    logger.debug("trade deleted for id: " + id);
    return "redirect:/trade/list";
  }
}
