package com.nnk.springboot.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {HomeController.class})
@ExtendWith(SpringExtension.class)
public class HomeControllerTest {
  @Autowired
  private HomeController homeController;

  @Test
  public void testAdminHome() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/home");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.homeController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(0))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/bidList/list"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/bidList/list"));
  }

  @Test
  public void testAdminHome2() throws Exception {
    MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/admin/home");
    getResult.contentType("Not all who wander are lost");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.homeController).build();
    ResultActions actualPerformResult = buildResult.perform(getResult);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(0))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/bidList/list"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/bidList/list"));
  }

  @Test
  public void testHome() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.homeController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(0))
            .andExpect(MockMvcResultMatchers.view().name("home"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("home"));
  }

  @Test
  public void testHome2() throws Exception {
    MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/");
    getResult.contentType("Not all who wander are lost");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.homeController).build();
    ResultActions actualPerformResult = buildResult.perform(getResult);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(0))
            .andExpect(MockMvcResultMatchers.view().name("home"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("home"));
  }
}

