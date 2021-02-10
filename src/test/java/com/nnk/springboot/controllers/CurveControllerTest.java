package com.nnk.springboot.controllers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointServiceImpl;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CurveController.class})
@ExtendWith(SpringExtension.class)
public class CurveControllerTest {
  @Autowired
  private CurveController curveController;

  @MockBean
  private CurvePointServiceImpl curvePointServiceImpl;

  @Test
  public void testAddCurveForm() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/curvePoint/add");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.curveController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("curvePoint"))
            .andExpect(MockMvcResultMatchers.view().name("curvePoint/add"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("curvePoint/add"));
  }

  @Test
  public void testAddCurveForm2() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/curvePoint/add", "uriVars");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.curveController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("curvePoint"))
            .andExpect(MockMvcResultMatchers.view().name("curvePoint/add"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("curvePoint/add"));
  }

  @Test
  public void testHome() throws Exception {
    when(this.curvePointServiceImpl.findAll()).thenReturn(new ArrayList<CurvePoint>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/curvePoint/list");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.curveController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("curvePoint"))
            .andExpect(MockMvcResultMatchers.view().name("curvePoint/list"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("curvePoint/list"));
  }

  @Test
  public void testHome2() throws Exception {
    when(this.curvePointServiceImpl.findAll()).thenReturn(new ArrayList<CurvePoint>());
    MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/curvePoint/list");
    getResult.contentType("Not all who wander are lost");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.curveController).build();
    ResultActions actualPerformResult = buildResult.perform(getResult);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("curvePoint"))
            .andExpect(MockMvcResultMatchers.view().name("curvePoint/list"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("curvePoint/list"));
  }

  @Test
  public void testUpdateCurve() throws Exception {
    when(this.curvePointServiceImpl.findById((Integer) any())).thenReturn(Optional.<CurvePoint>empty());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/curvePoint/update/{id}", 1)
            .param("curveId", "123");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.curveController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("curvePoint"))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/curvePoint/list"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/curvePoint/list"));
  }

  @Test
  public void testUpdateCurve2() throws Exception {
    when(this.curvePointServiceImpl.findById((Integer) any())).thenReturn(Optional.<CurvePoint>empty());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/curvePoint/update/{id}", 1)
            .param("curveId", "value");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.curveController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("curvePoint"))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/curvePoint/list"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/curvePoint/list"));
  }

  @Test
  public void testValidate() throws Exception {
    doNothing().when(this.curvePointServiceImpl).save((com.nnk.springboot.domain.CurvePoint) any());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/curvePoint/validate")
            .param("curveId", "123");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.curveController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("curvePoint"))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/curvePoint/list"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/curvePoint/list"));
  }

  @Test
  public void testValidate2() throws Exception {
    doNothing().when(this.curvePointServiceImpl).save((com.nnk.springboot.domain.CurvePoint) any());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/curvePoint/validate")
            .param("curveId", "value");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.curveController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("curvePoint"))
            .andExpect(MockMvcResultMatchers.view().name("curvePoint/list"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("curvePoint/list"));
  }
}

