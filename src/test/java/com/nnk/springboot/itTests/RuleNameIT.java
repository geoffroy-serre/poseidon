package com.nnk.springboot.itTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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
public class RuleNameIT {

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  RuleNameService ruleNameService;

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

    RuleName ruleName = new RuleName();
    ruleName.setName("rule");
    ruleName.setDescription("description");
    ruleName.setJson("Json");
    ruleName.setSqlPart("sqlpart");
    ruleName.setTemplate("template");
    ruleName.setSqlStr("sqlstr");

    MvcResult result = this.mockMvc.perform(post("/ruleName/validate")
            .flashAttr("ruleName", ruleName)
            .content(objectMapper.writeValueAsString(ruleName))
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(302))
            .andExpect(redirectedUrl("/ruleName/list"))
            .andReturn();
    int id = ruleNameService.findAll().get(0).getId();
    assertEquals(ruleNameService.findById(id).get().getName(), "rule");
    assertEquals(ruleNameService.findById(id).get().getDescription(), "description");
    assertEquals(ruleNameService.findById(id).get().getJson(), "Json");
    assertEquals(ruleNameService.findById(id).get().getSqlStr(), "sqlstr");
    assertEquals(ruleNameService.findById(id).get().getSqlPart(), "sqlpart");
    assertEquals(ruleNameService.findById(id).get().getTemplate(), "template");

    assertNull(result.getResponse().getErrorMessage());

    //Deleting user created  for this test using direct call to user Service

    ruleNameService.delete(ruleNameService.findById(id).get());

  }

  @Test
  void addForm() throws Exception {
    MvcResult mvcResult = this.mockMvc.perform(get("/ruleName/add")
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(200))
            .andReturn();
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Json"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("SqlStr"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("SqlPart"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Template"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Description"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Name"));
  }

  @Test
  void UpdateForm() throws Exception {
    RuleName ruleName = new RuleName();
    ruleName.setName("rule");
    ruleName.setDescription("description");
    ruleName.setJson("Json");
    ruleName.setSqlPart("sqlpart");
    ruleName.setTemplate("template");
    ruleName.setSqlStr("sqlstr");
    ruleNameService.save(ruleName);

    int id = ruleNameService.findAll().get(0).getId();
    MvcResult mvcResult = this.mockMvc.perform(get("/ruleName/update/" + id)
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(200))
            .andReturn();
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Json"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("SqlStr"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("SqlPart"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Template"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Description"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Name"));

    ruleNameService.delete(ruleNameService.findById(id).get());
  }

  @Test
  void getRuleNAmeList() throws Exception {
    RuleName ruleName = new RuleName();
    ruleName.setName("rule");
    ruleName.setDescription("description");
    ruleName.setJson("Json");
    ruleName.setSqlPart("sqlpart");
    ruleName.setTemplate("template");
    ruleName.setSqlStr("sqlstr");
    ruleNameService.save(ruleName);
    MvcResult mvcResult = this.mockMvc.perform(get("/ruleName/list").
            with(user("Geff")
                    .roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(200))
            .andReturn();

    assertTrue(mvcResult.getResponse().getContentAsString().contains("Json"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("SqlStr"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("SqlPart"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Template"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Description"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("Name"));

    ruleNameService.delete(ruleNameService.findAll().get(0));

  }

  @Test
  void ratingUpdate() throws Exception {
    RuleName ruleName = new RuleName();
    ruleName.setName("rule");
    ruleName.setDescription("description");
    ruleName.setJson("Json");
    ruleName.setSqlPart("sqlpart");
    ruleName.setTemplate("template");
    ruleName.setSqlStr("sqlstr");
    ruleNameService.save(ruleName);

    int id = ruleNameService.findAll().get(0).getId();
    RuleName updated = ruleNameService.findById(id).get();
    updated.setDescription("feetch");

    MvcResult result = this.mockMvc.perform(post("/ruleName/update/" + id)
            .flashAttr("ruleName", updated)
            .content(objectMapper.writeValueAsString(updated))
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(302))
            .andExpect(redirectedUrl("/ruleName/list"))
            .andReturn();

    assertTrue(ruleNameService.findById(id).get().getDescription().equals("feetch"));
    assertNull(result.getResponse().getErrorMessage());


    // ReSet to original values
    ruleNameService.delete(updated);

  }

  @Test
  void UpdateFail() throws Exception {
    RuleName ruleName = new RuleName();
    ruleName.setName("rule");
    ruleName.setDescription("description");
    ruleName.setJson("Json");
    ruleName.setSqlPart("sqlpart");
    ruleName.setTemplate("template");
    ruleName.setSqlStr("sqlstr");
    ruleName.setId(1);


    MvcResult result = this.mockMvc.perform(post("/ruleName/update/1")
            .flashAttr("ruleName", ruleName)
            .content(objectMapper.writeValueAsString(ruleName))
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(302))
            .andExpect(redirectedUrl("/ruleName/list"))
            .andReturn();

    assertTrue(ruleNameService.findAll().isEmpty());


    // ReSet to original values
    ruleNameService.delete(ruleName);

  }

  @Test
  void delete() throws Exception {
    RuleName ruleName = new RuleName();
    ruleName.setName("rule");
    ruleName.setDescription("description");
    ruleName.setJson("Json");
    ruleName.setSqlPart("sqlpart");
    ruleName.setTemplate("template");
    ruleName.setSqlStr("sqlstr");
    ruleNameService.save(ruleName);

    int id = ruleNameService.findAll().get(0).getId();


    MvcResult result = this.mockMvc.perform(get("/ruleName/delete/" + id)
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(302))
            .andExpect(redirectedUrl("/ruleName/list"))
            .andReturn();

    assertFalse(ruleNameService.findById(id).isPresent());
    assertNull(result.getResponse().getErrorMessage());


  }


}
