package com.nnk.springboot.controllers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserServiceImpl;
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

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
public class UserControllerTest {
  @Autowired
  private UserController userController;

  @MockBean
  private UserServiceImpl userServiceImpl;

  @Test
  public void testAddUser() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/add");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.userController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
            .andExpect(MockMvcResultMatchers.view().name("user/add"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("user/add"));
  }

  @Test
  public void testAddUser2() throws Exception {
    MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/user/add");
    getResult.contentType("Not all who wander are lost");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.userController).build();
    ResultActions actualPerformResult = buildResult.perform(getResult);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
            .andExpect(MockMvcResultMatchers.view().name("user/add"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("user/add"));
  }

  @Test
  public void testDeleteUser() throws Exception {
    User user = new User();
    user.setPassword("iloveyou");
    user.setRole("Role");
    user.setUsername("janedoe");
    user.setId(1);
    user.setFullname("Dr Jane Doe");
    Optional<User> ofResult = Optional.<User>of(user);
    when(this.userServiceImpl.findAll()).thenReturn(new ArrayList<User>());
    doNothing().when(this.userServiceImpl).delete((User) any());
    when(this.userServiceImpl.findById((Integer) any())).thenReturn(ofResult);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/delete/{id}", 1);
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.userController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/user/list"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/user/list"));
  }

  @Test
  public void testHome() throws Exception {
    when(this.userServiceImpl.findAll()).thenReturn(new ArrayList<User>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/list");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.userController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
            .andExpect(MockMvcResultMatchers.view().name("user/list"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("user/list"));
  }

  @Test
  public void testHome2() throws Exception {
    when(this.userServiceImpl.findAll()).thenReturn(new ArrayList<User>());
    MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/user/list");
    getResult.contentType("Not all who wander are lost");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.userController).build();
    ResultActions actualPerformResult = buildResult.perform(getResult);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
            .andExpect(MockMvcResultMatchers.view().name("user/list"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("user/list"));
  }

  @Test
  public void testShowUpdateForm() throws Exception {
    User user = new User();
    user.setPassword("iloveyou");
    user.setRole("Role");
    user.setUsername("janedoe");
    user.setId(1);
    user.setFullname("Dr Jane Doe");
    Optional<User> ofResult = Optional.<User>of(user);
    when(this.userServiceImpl.findById((Integer) any())).thenReturn(ofResult);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/update/{id}", 1);
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.userController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
            .andExpect(MockMvcResultMatchers.view().name("user/update"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("user/update"));
  }

  @Test
  public void testUpdateUser() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/update/{id}", 1);
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.userController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
            .andExpect(MockMvcResultMatchers.view().name("user/update"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("user/update"));
  }

  @Test
  public void testValidate() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/validate");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.userController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
            .andExpect(MockMvcResultMatchers.view().name("user/add"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("user/add"));
  }
}

