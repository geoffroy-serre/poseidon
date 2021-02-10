package com.nnk.springboot.ittest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserIT {

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  UserService userService;


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
  void userValidateWorkForAdmin() throws Exception {

    User userTest = new User();
    userTest.setPassword("pwd");
    userTest.setUsername("Geff");
    userTest.setFullname("fullName");
    userTest.setRole("ADMIN");


    MvcResult result = this.mockMvc.perform(post("/user/validate")
            .flashAttr("user", userTest)
            .content(objectMapper.writeValueAsString(userTest))
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(302))
            .andExpect(redirectedUrl("/user/add"))
            .andReturn();

    assertEquals(userService.findByUsername("Geff").getUsername(), "Geff");
    assertEquals(userService.findByUsername("Geff").getFullname(), "fullName");
    assertEquals(userService.findByUsername("Geff").getRole(), "ADMIN");
    Assertions.assertNull(result.getResponse().getErrorMessage());

    //Deleting created user for this test using direct call to user Service
    userTest.setId(userService.findByUsername("Geff").getId());
    userService.delete(userTest);

  }

  @Test
  void userValidateForAdminNotValid() throws Exception {

    User userTest = new User();
    userTest.setUsername("Geff");

    System.out.println(objectMapper.writeValueAsString(userTest));
    MvcResult mvcResult = this.mockMvc.perform(post("/user/validate")
            .flashAttr("user", userTest)
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(302))
            .andExpect(redirectedUrl("/user/add"))
            .andReturn();

    Assertions.assertNull(userService.findByUsername("Geff"));

  }

  @Test
  void userValidateDontWorkForUser() throws Exception {

    User userTest = new User();
    userTest.setUsername("Geff");
    userTest.setRole("ADMIN");
    userTest.setFullname("Geff LeDev");
    userTest.setPassword("pwd"); //TODO dois etre false quand il y aura les regles de pwd
    System.out.println(objectMapper.writeValueAsString(userTest));
    this.mockMvc.perform(post("/user/validate").with(user("Geff").roles("USER"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userTest)))
            .andExpect(status().is(403));


  }

  @Test
  void getUserList() throws Exception {

    MvcResult mvcResult = this.mockMvc.perform(get("/user/list").with(user("Geff").roles("USER"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(200))
            .andReturn();

    Assertions.assertTrue(mvcResult.getResponse().getContentAsString().contains("admin"));
    Assertions.assertTrue(mvcResult.getResponse().getContentAsString().contains("user"));


  }

}
