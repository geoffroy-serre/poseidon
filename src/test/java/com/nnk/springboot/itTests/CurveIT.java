package com.nnk.springboot.itTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CurveIT {

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  CurvePointService curvePointService;
  @Autowired
  CurvePointRepository curvePointRepository;
  @Autowired
  private WebApplicationContext wac;
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    this.mockMvc =
            MockMvcBuilders.webAppContextSetup(this.wac)
                    .apply(springSecurity())
                    .build();
  }

  @Test
  void curveValidateWork() throws Exception {

    CurvePoint curve = new CurvePoint();
    curve.setCurveId(50);
    curve.setValue(22.1);
    curve.setTerm(15.1);
    curve.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));

    MvcResult result = this.mockMvc.perform(post("/curvePoint/validate")
            .flashAttr("curvePoint", curve)
            .content(objectMapper.writeValueAsString(curve))
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(302))
            .andExpect(redirectedUrl("/curvePoint/list"))
            .andReturn();

    List<CurvePoint> resultCurves = curvePointService.findAll();
    System.out.println(resultCurves.size());
    assertEquals(resultCurves.get(0).getCurveId(), curve.getCurveId());
    assertEquals(resultCurves.get(0).getTerm(), curve.getTerm());
    assertEquals(resultCurves.get(0).getValue(), curve.getValue());
    Assertions.assertNull(result.getResponse().getErrorMessage());

    //Deleting curve created  for this test using direct call to user Service
    curvePointService.deleteAll();

  }


  @Test
  void curveValidateForAdminNotValid() throws Exception {

    CurvePoint curvePoint = new CurvePoint();

    MvcResult mvcResult = this.mockMvc.perform(post("/curvePoint/validate")
            .flashAttr("curvePoint", curvePoint)
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(302))
            .andExpect(redirectedUrl("/curvePoint/add"))
            .andReturn();

    Assertions.assertTrue(curvePointService.findAll().isEmpty());

  }


  @Test
  void getCurveList() throws Exception {

    CurvePoint curve = new CurvePoint();
    curve.setCurveId(50);
    curve.setValue(22.1);
    curve.setTerm(15.1);
    curve.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
    curvePointService.save(curve);
    MvcResult mvcResult = this.mockMvc.perform(get("/curvePoint/list").with(user("Geff").roles(
            "ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(200))
            .andReturn();

    assertTrue(mvcResult.getResponse().getContentAsString().contains("50"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("22.1"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("15.1"));

  }

  @Test
  void curveUpdate() throws Exception {
    CurvePoint curve = new CurvePoint();
    curve.setCurveId(50);
    curve.setValue(22.1);
    curve.setTerm(15.1);
    curve.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
    curvePointService.save(curve);

    CurvePoint toUpdate = curvePointService.findAll().get(0);
    toUpdate.setCurveId(52);

    MvcResult result = this.mockMvc.perform(post("/curvePoint/update/" + toUpdate.getId())
            .flashAttr("curvePoint", toUpdate)
            .content(objectMapper.writeValueAsString(toUpdate))
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(302))
            .andExpect(redirectedUrl("/curvePoint/list"))
            .andReturn();

    assertTrue(curvePointService.findById(toUpdate.getId()).get().getCurveId() == 52);
    Assertions.assertNull(result.getResponse().getErrorMessage());


    // ReSet to original values
    curvePointService.deleteAll();

  }

  @Test
  void curveUpdateFail() throws Exception {
    CurvePoint curve = new CurvePoint();
    curve.setId(1);
    curve.setCurveId(50);
    curve.setValue(22.1);
    curve.setTerm(15.1);
    curve.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));


    MvcResult result = this.mockMvc.perform(post("/curvePoint/update/" + curve.getId())
            .flashAttr("curvePoint", curve)
            .content(objectMapper.writeValueAsString(curve))
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(302))
            .andExpect(redirectedUrl("/curvePoint/list"))
            .andReturn();

    assertFalse(curvePointService.findById(curve.getId()).isPresent());
    Assertions.assertNull(result.getResponse().getErrorMessage());


    // ReSet to original values
    curvePointService.deleteAll();

  }


  @Test
  void deleteCurve() throws Exception {
    CurvePoint curve = new CurvePoint();
    curve.setCurveId(50);
    curve.setValue(22.1);
    curve.setTerm(15.1);
    curve.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
    curvePointService.save(curve);

    int id = curvePointService.findAll().get(0).getId();

    MvcResult result = this.mockMvc.perform(get("/curvePoint/delete/" + id)
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(302))
            .andExpect(redirectedUrl("/curvePoint/list"))
            .andReturn();

    assertFalse(curvePointService.findById(id).isPresent());
    assertNull(result.getResponse().getErrorMessage());


  }

  @Test
  void addForm() throws Exception {
    MvcResult mvcResult = this.mockMvc.perform(get("/curvePoint/add")
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(200))
            .andReturn();
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Curve Id"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Term"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Bid Quantity"));

  }

  @Test
  void updateForm() throws Exception {
    CurvePoint curve = new CurvePoint();
    curve.setCurveId(50);
    curve.setValue(22.1);
    curve.setTerm(15.1);
    curve.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
    curvePointService.save(curve);

    int id = curvePointService.findAll().get(0).getId();
    MvcResult mvcResult = this.mockMvc.perform(get("/curvePoint/update/" + id)
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(200))
            .andReturn();
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Curve Id"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Term"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Bid Quantity"));

    curvePointService.delete(curvePointService.findAll().get(0));
  }


}
