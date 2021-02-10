package com.nnk.springboot.controllers;

import static org.mockito.Mockito.when;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListServiceImpl;
import java.util.ArrayList;
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

@ContextConfiguration(classes = {BidListController.class})
@ExtendWith(SpringExtension.class)
public class BidListControllerTest {
  @Autowired
  private BidListController bidListController;

  @MockBean
  private BidListServiceImpl bidListServiceImpl;

  @Test
  public void testAddBidForm() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/bidList/add");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.bidListController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("bidList"))
            .andExpect(MockMvcResultMatchers.view().name("bidList/add"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("bidList/add"));
  }

  @Test
  public void testAddBidForm2() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/bidList/add", "uriVars");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.bidListController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("bidList"))
            .andExpect(MockMvcResultMatchers.view().name("bidList/add"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("bidList/add"));
  }

  @Test
  public void testHome() throws Exception {
    when(this.bidListServiceImpl.findAll()).thenReturn(new ArrayList<BidList>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/bidList/list");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.bidListController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("bidList"))
            .andExpect(MockMvcResultMatchers.view().name("bidList/list"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("bidList/list"));
  }

  @Test
  public void testHome2() throws Exception {
    when(this.bidListServiceImpl.findAll()).thenReturn(new ArrayList<BidList>());
    MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/bidList/list");
    getResult.contentType("Not all who wander are lost");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.bidListController).build();
    ResultActions actualPerformResult = buildResult.perform(getResult);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("bidList"))
            .andExpect(MockMvcResultMatchers.view().name("bidList/list"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("bidList/list"));
  }

  @Test
  public void testUpdateBid() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/bidList/update/{id}", 1);
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.bidListController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("bidList"))
            .andExpect(MockMvcResultMatchers.view().name("/bidList/list"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("/bidList/list"));
  }

  @Test
  public void testValidate() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/bidList/validate");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.bidListController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("bidList"))
            .andExpect(MockMvcResultMatchers.view().name("bidList/list"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("bidList/list"));
  }
}

