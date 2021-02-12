package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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
public class RuleNameController {
  private static final Logger logger = LoggerFactory.getLogger(RuleNameController.class);

  @Autowired
  RuleNameService ruleNameService;

  @RequestMapping("/ruleName/list")
  public String home(Model model) {

    model.addAttribute("ruleNames", ruleNameService.findAll());
    logger.debug("rulename list displayed");
    return "ruleName/list";
  }

  @GetMapping("/ruleName/add")
  public String addRuleForm(RuleName ruleName) {
    logger.debug("ruleName add form displayed ");
    return "ruleName/add";
  }

  @PostMapping("/ruleName/validate")
  public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {

    if (!result.hasErrors()) {
      ruleNameService.save(ruleName);
      logger.debug("ruleNAme validated: ");
      return "redirect:/ruleName/list";
    }
    logger.debug("ruleName not validated " + result.getAllErrors().toString());
    return "ruleName/add";
  }

  @GetMapping("/ruleName/update/{id}")
  public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

    RuleName ruleName =
            ruleNameService.findById(id).orElseThrow(() -> new IllegalArgumentException(
                    "Invalid Trade id: " + id));

    model.addAttribute("ruleName", ruleName);
    logger.debug("ruleNAme update form sent for id: " + id);
    return "ruleName/update";
  }

  @PostMapping("/ruleName/update/{id}")
  public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                               BindingResult result, Model model) {

    if (!result.hasErrors() && ruleNameService.findById(id).isPresent()) {
      ruleName.setId(id);
      ruleNameService.save(ruleName);
      logger.debug("ruleName updated for id: " + id);
      return "redirect:/ruleName/list";
    }
    logger.debug("ruleName not updated for id: " + id + " " + result.getAllErrors().toString());
    return "redirect:/ruleName/list";
  }

  @GetMapping("/ruleName/delete/{id}")
  public String deleteRuleName(@PathVariable("id") Integer id, Model model) {

    ruleNameService.deleteById(id);
    logger.debug("ruleName deleted for id: " + id);
    return "redirect:/ruleName/list";
  }
}
