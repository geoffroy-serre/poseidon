package com.nnk.springboot.ittest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    User userTest = new User(0,"Geff","pwd","fullName","ADMIN");
    System.out.println(objectMapper.writeValueAsString(userTest));
    MvcResult result = this.mockMvc.perform(post("/user/validate").with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userTest)))
            .andExpect(status().is(200))
            .andReturn();

   /* User result = userService.findByUsername("Geff");
    assertEquals("Geff", result.getUsername());
    assertEquals("Geff LeDev", result.getFullname());

    this.mockMvc.perform(get("/user/delete/{id}",userService.findByUsername("Geff").getId())
    .contentType(MediaType.APPLICATION_JSON)
    ).andExpect(status().is(200));*/

  }

  @Test
  void userValidateWorkForAdminNotValid() throws Exception {

    User userTest = new User();
    System.out.println(objectMapper.writeValueAsString(userTest));
    this.mockMvc.perform(post("/user/validate").with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userTest)))
            .andExpect(status().is(200));

   /* User result = userService.findByUsername("Geff");
    assertEquals("Geff", result.getUsername());
    assertEquals("Geff LeDev", result.getFullname());

    this.mockMvc.perform(get("/user/delete/{id}",userService.findByUsername("Geff").getId())
    .contentType(MediaType.APPLICATION_JSON)
    ).andExpect(status().is(200));*/

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
