package com.nnk.springboot.controllers;


import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
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
public class CurveController {
  private static final Logger logger = LoggerFactory.getLogger(CurveController.class);
  // TODO: Inject Curve Point service
  @Autowired
  CurvePointService curvePointService;

  @RequestMapping("/curvePoint/list")
  public String home(Model model) {

    model.addAttribute("curvePoint", curvePointService.findAll());
    logger.debug("curve list displayed");
    return "curvePoint/list";
  }

  @GetMapping("/curvePoint/add")
  public String addCurveForm(CurvePoint curvePoint) {
    logger.debug("curve add displayed");
    return "curvePoint/add";
  }

  @PostMapping("/curvePoint/validate")
  public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
    if (!result.hasErrors()) {
      curvePoint.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
      curvePointService.save(curvePoint);
      logger.debug("curve validated ");
      return "redirect:/curvePoint/list";
    }
    logger.debug("curve not validated " + result.getAllErrors().toString());
    return "redirect:/curvePoint/add";
  }

  @GetMapping("/curvePoint/update/{id}")
  public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

    CurvePoint curvePoint =
            curvePointService.findById(id).orElseThrow(() -> new IllegalArgumentException(
                    "Invalid CurvePoint id: " + id));
    model.addAttribute("curvePoint", curvePoint);
    logger.debug("curve update form displayed for id: " + id);
    return "curvePoint/update";
  }

  @PostMapping("/curvePoint/update/{id}")
  public String updateCurve(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                            BindingResult result, Model model) {
    System.out.println(curvePoint.toString());
    // TODO: check required fields, if valid call service to update Curve and return Curve list
    if (!result.hasErrors() && curvePointService.findById(id).isPresent()) {
      curvePoint.setId(id);
      curvePoint.setAsOfDate(Timestamp.valueOf(LocalDateTime.now()));
      curvePointService.save(curvePoint);
      logger.debug("curve updated for id: " + id);
      return "redirect:/curvePoint/list";
    }
    logger.debug("curve not updated for Id " + id + " " + result.getAllErrors().toString());
    return "redirect:/curvePoint/list";
  }

  @GetMapping("/curvePoint/delete/{id}")
  public String deleteCurve(@PathVariable("id") Integer id, Model model) {
    // TODO: Find Curve by Id and delete the Curve, return to Curve list
    CurvePoint curvePoint =
            curvePointService.findById(id).orElseThrow(() -> new IllegalArgumentException(
                    "Invalid CurvePoint id: " + id));
    curvePointService.delete(curvePoint);
    logger.debug("curve deleted for id: " + id);
    return "redirect:/curvePoint/list";
  }
}
