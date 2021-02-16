package com.nnk.springboot.itTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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
public class TradeIT {

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  TradeService tradeService;

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
  void ValidateWorkForAdmin() throws Exception {

    Trade trade = new Trade();
    trade.setAccount("account");
    trade.setType("type");
    trade.setBuyQuantity(20.0);


    MvcResult result = this.mockMvc.perform(post("/trade/validate")
            .flashAttr("trade", trade)
            .content(objectMapper.writeValueAsString(trade))
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(302))
            .andExpect(redirectedUrl("/trade/list"))
            .andReturn();
    int id = tradeService.findAll().get(0).getId();
    assertEquals(tradeService.findById(id).get().getAccount(), "account");
    assertEquals(tradeService.findById(id).get().getType(), "type");
    assertEquals(tradeService.findById(id).get().getBuyQuantity(), 20.0);


    assertNull(result.getResponse().getErrorMessage());

    //Deleting user created  for this test using direct call to user Service

    tradeService.delete(tradeService.findById(id).get());

  }

  @Test
  void ValidateFailForAdmin() throws Exception {

    Trade trade = new Trade();
    trade.setAccount("account");

    MvcResult result = this.mockMvc.perform(post("/trade/validate")
            .flashAttr("trade", trade)
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(200))
            .andReturn();

    assertTrue(tradeService.findAll().isEmpty());

    //Deleting user created  for this test using direct call to user Service

    tradeService.deleteAll();

  }


  @Test
  void addForm() throws Exception {
    MvcResult mvcResult = this.mockMvc.perform(get("/trade/add")
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(200))
            .andReturn();
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Account"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Buy Quantity"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Type"));
  }

  @Test
  void UpdateForm() throws Exception {
    Trade trade = new Trade();
    trade.setAccount("account");
    trade.setType("type");
    trade.setBuyQuantity(20.0);
    tradeService.save(trade);

    int id = tradeService.findAll().get(0).getId();
    MvcResult mvcResult = this.mockMvc.perform(get("/trade/update/" + id)
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(200))
            .andReturn();
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Account"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Buy Quantity"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Type"));

    tradeService.delete(tradeService.findAll().get(0));
  }


  @Test
  void getTradeList() throws Exception {
    Trade trade = new Trade();
    trade.setAccount("account");
    trade.setType("type");
    trade.setBuyQuantity(20.0);
    tradeService.save(trade);

    MvcResult mvcResult = this.mockMvc.perform(get("/trade/list").with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(200))
            .andReturn();

    assertTrue(mvcResult.getResponse().getContentAsString().contains("Account"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Buy Quantity"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Type"));

    tradeService.delete(tradeService.findAll().get(0));

  }

  @Test
  void tradeUpdate() throws Exception {
    Trade trade = new Trade();
    trade.setAccount("account");
    trade.setType("type");
    trade.setBuyQuantity(20.0);
    tradeService.save(trade);

    int id = tradeService.findAll().get(0).getId();
    Trade updated = tradeService.findById(id).get();
    updated.setAccount("feetch");

    MvcResult result = this.mockMvc.perform(post("/trade/update/" + id)
            .flashAttr("trade", updated)
            .content(objectMapper.writeValueAsString(updated))
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(302))
            .andExpect(redirectedUrl("/trade/list"))
            .andReturn();

    assertTrue(tradeService.findById(id).get().getAccount().equals("feetch"));
    assertNull(result.getResponse().getErrorMessage());


    // ReSet to original values
    tradeService.delete(updated);

  }

  @Test
  void ratingUpdateFail() throws Exception {
    Trade trade = new Trade();
    trade.setAccount("account");
    trade.setType("type");
    trade.setBuyQuantity(20.0);
    trade.setId(1);


    MvcResult result = this.mockMvc.perform(post("/trade/update/1")
            .flashAttr("trade", trade)
            .content(objectMapper.writeValueAsString(trade))
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(302))
            .andExpect(redirectedUrl("/trade/list"))
            .andReturn();

    assertTrue(tradeService.findAll().isEmpty());


    // ReSet to original values
    tradeService.delete(trade);

  }


  @Test
  void deleteUser() throws Exception {
    Trade trade = new Trade();
    trade.setAccount("account");
    trade.setType("type");
    trade.setBuyQuantity(20.0);
    tradeService.save(trade);

    tradeService.save(trade);

    int id = tradeService.findAll().get(0).getId();


    MvcResult result = this.mockMvc.perform(get("/trade/delete/" + id)
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(302))
            .andExpect(redirectedUrl("/trade/list"))
            .andReturn();

    assertFalse(tradeService.findById(id).isPresent());
    assertNull(result.getResponse().getErrorMessage());


  }


}
