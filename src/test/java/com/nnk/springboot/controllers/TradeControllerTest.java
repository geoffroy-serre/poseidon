package com.nnk.springboot.controllers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeServiceImpl;
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

@ContextConfiguration(classes = {TradeController.class})
@ExtendWith(SpringExtension.class)
public class TradeControllerTest {
  @Autowired
  private TradeController tradeController;

  @MockBean
  private TradeServiceImpl tradeServiceImpl;

  @Test
  public void testAddUser() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/trade/add");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.tradeController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("trade"))
            .andExpect(MockMvcResultMatchers.view().name("trade/add"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("trade/add"));
  }

  @Test
  public void testAddUser2() throws Exception {
    MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/trade/add");
    getResult.contentType("Not all who wander are lost");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.tradeController).build();
    ResultActions actualPerformResult = buildResult.perform(getResult);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("trade"))
            .andExpect(MockMvcResultMatchers.view().name("trade/add"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("trade/add"));
  }

  @Test
  public void testHome() throws Exception {
    when(this.tradeServiceImpl.findAll()).thenReturn(new ArrayList<Trade>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/trade/list");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.tradeController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("trade"))
            .andExpect(MockMvcResultMatchers.view().name("trade/list"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("trade/list"));
  }

  @Test
  public void testHome2() throws Exception {
    when(this.tradeServiceImpl.findAll()).thenReturn(new ArrayList<Trade>());
    MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/trade/list");
    getResult.contentType("Not all who wander are lost");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.tradeController).build();
    ResultActions actualPerformResult = buildResult.perform(getResult);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("trade"))
            .andExpect(MockMvcResultMatchers.view().name("trade/list"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("trade/list"));
  }

  @Test
  public void testUpdateTrade() throws Exception {
    when(this.tradeServiceImpl.findById((Integer) any())).thenReturn(Optional.<Trade>empty());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/trade/update/{id}", 1);
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.tradeController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("trade"))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/trade/list"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/trade/list"));
  }

  @Test
  public void testUpdateTrade2() throws Exception {
    when(this.tradeServiceImpl.findById((Integer) any())).thenReturn(Optional.<Trade>empty());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/trade/update/{id}", 1)
            .param("buyQuantity", "Buy Quantity");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.tradeController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("trade"))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/trade/list"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/trade/list"));
  }

  @Test
  public void testValidate() throws Exception {
    doNothing().when(this.tradeServiceImpl).save((com.nnk.springboot.domain.Trade) any());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/trade/validate");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.tradeController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("trade"))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/trade/list"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/trade/list"));
  }

  @Test
  public void testValidate2() throws Exception {
    doNothing().when(this.tradeServiceImpl).save((com.nnk.springboot.domain.Trade) any());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/trade/validate")
            .param("buyQuantity", "Buy Quantity");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.tradeController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("trade"))
            .andExpect(MockMvcResultMatchers.view().name("trade/add"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("trade/add"));
  }
}

