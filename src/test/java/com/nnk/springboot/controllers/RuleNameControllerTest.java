package com.nnk.springboot.controllers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameServiceImpl;
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

@ContextConfiguration(classes = {RuleNameController.class})
@ExtendWith(SpringExtension.class)
public class RuleNameControllerTest {
  @Autowired
  private RuleNameController ruleNameController;

  @MockBean
  private RuleNameServiceImpl ruleNameServiceImpl;

  @Test
  public void testAddRuleForm() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ruleName/add");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.ruleNameController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("ruleName"))
            .andExpect(MockMvcResultMatchers.view().name("ruleName/add"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("ruleName/add"));
  }

  @Test
  public void testAddRuleForm2() throws Exception {
    MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/ruleName/add");
    getResult.contentType("Not all who wander are lost");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.ruleNameController).build();
    ResultActions actualPerformResult = buildResult.perform(getResult);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("ruleName"))
            .andExpect(MockMvcResultMatchers.view().name("ruleName/add"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("ruleName/add"));
  }

  @Test
  public void testDeleteRuleName() throws Exception {
    RuleName ruleName = new RuleName();
    ruleName.setSqlStr("Sql Str");
    ruleName.setId(1);
    ruleName.setTemplate("Template");
    ruleName.setName("Name");
    ruleName.setDescription("The characteristics of someone or something");
    ruleName.setJson("Json");
    ruleName.setSqlPart("Sql Part");
    Optional<RuleName> ofResult = Optional.<RuleName>of(ruleName);
    doNothing().when(this.ruleNameServiceImpl).delete((RuleName) any());
    when(this.ruleNameServiceImpl.findById((Integer) any())).thenReturn(ofResult);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ruleName/delete/{id}", 1);
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.ruleNameController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(0))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/ruleName/list"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/ruleName/list"));
  }

  @Test
  public void testHome() throws Exception {
    when(this.ruleNameServiceImpl.findAll()).thenReturn(new ArrayList<RuleName>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ruleName/list");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.ruleNameController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("ruleName"))
            .andExpect(MockMvcResultMatchers.view().name("ruleName/list"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("ruleName/list"));
  }

  @Test
  public void testHome2() throws Exception {
    when(this.ruleNameServiceImpl.findAll()).thenReturn(new ArrayList<RuleName>());
    MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/ruleName/list");
    getResult.contentType("Not all who wander are lost");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.ruleNameController).build();
    ResultActions actualPerformResult = buildResult.perform(getResult);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("ruleName"))
            .andExpect(MockMvcResultMatchers.view().name("ruleName/list"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("ruleName/list"));
  }

  @Test
  public void testShowUpdateForm() throws Exception {
    RuleName ruleName = new RuleName();
    ruleName.setSqlStr("Sql Str");
    ruleName.setId(1);
    ruleName.setTemplate("Template");
    ruleName.setName("Name");
    ruleName.setDescription("The characteristics of someone or something");
    ruleName.setJson("Json");
    ruleName.setSqlPart("Sql Part");
    Optional<RuleName> ofResult = Optional.<RuleName>of(ruleName);
    when(this.ruleNameServiceImpl.findById((Integer) any())).thenReturn(ofResult);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ruleName/update/{id}", 1);
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.ruleNameController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("ruleName"))
            .andExpect(MockMvcResultMatchers.view().name("ruleName/update"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("ruleName/update"));
  }

  @Test
  public void testUpdateRuleName() throws Exception {
    RuleName ruleName = new RuleName();
    ruleName.setSqlStr("Sql Str");
    ruleName.setId(1);
    ruleName.setTemplate("Template");
    ruleName.setName("Name");
    ruleName.setDescription("The characteristics of someone or something");
    ruleName.setJson("Json");
    ruleName.setSqlPart("Sql Part");
    Optional<RuleName> ofResult = Optional.<RuleName>of(ruleName);
    doNothing().when(this.ruleNameServiceImpl).save((RuleName) any());
    when(this.ruleNameServiceImpl.findById((Integer) any())).thenReturn(ofResult);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/ruleName/update/{id}", 1);
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.ruleNameController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("ruleName"))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/ruleName/list"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/ruleName/list"));
  }

  @Test
  public void testUpdateRuleName2() throws Exception {
    doNothing().when(this.ruleNameServiceImpl).save((RuleName) any());
    when(this.ruleNameServiceImpl.findById((Integer) any())).thenReturn(Optional.<RuleName>empty());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/ruleName/update/{id}", 1);
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.ruleNameController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("ruleName"))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/ruleName/list"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/ruleName/list"));
  }

  @Test
  public void testValidate() throws Exception {
    doNothing().when(this.ruleNameServiceImpl).save((com.nnk.springboot.domain.RuleName) any());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/ruleName/validate");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.ruleNameController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("ruleName"))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/ruleName/list"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/ruleName/list"));
  }
}

