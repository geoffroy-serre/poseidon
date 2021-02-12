package com.nnk.springboot.controllers;


import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
  // TODO: Inject Curve Point service
  @Autowired
  CurvePointService curvePointService;

  @RequestMapping("/curvePoint/list")
  public String home(Model model) {
    // TODO: find all Curve Point, add to model
    model.addAttribute("curvePoint", curvePointService.findAll());
    return "curvePoint/list";
  }

  @GetMapping("/curvePoint/add")
  public String addCurveForm(CurvePoint curvePoint) {
    return "curvePoint/add";
  }

  @PostMapping("/curvePoint/validate")
  public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
    // TODO: check data valid and save to db, after saving return Curve list
    if (!result.hasErrors()) {
      curvePoint.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
      curvePointService.save(curvePoint);
      return "redirect:/curvePoint/list";
    }
    return "redirect:/curvePoint/add";
  }

  @GetMapping("/curvePoint/update/{id}")
  public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    // TODO: get CurvePoint by Id and to model then show to the form
    CurvePoint curvePoint =
            curvePointService.findById(id).orElseThrow(() -> new IllegalArgumentException(
                    "Invalid CurvePoint id: " + id));
    model.addAttribute("curvePoint", curvePoint);

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

      return "redirect:/curvePoint/list";
    }
    System.out.println(curvePoint.toString());
    return "redirect:/curvePoint/list";
  }

  @GetMapping("/curvePoint/delete/{id}")
  public String deleteCurve(@PathVariable("id") Integer id, Model model) {
    // TODO: Find Curve by Id and delete the Curve, return to Curve list
    CurvePoint curvePoint =
            curvePointService.findById(id).orElseThrow(() -> new IllegalArgumentException(
                    "Invalid CurvePoint id: " + id));
    curvePointService.delete(curvePoint);

    return "redirect:/curvePoint/list";
  }
}
