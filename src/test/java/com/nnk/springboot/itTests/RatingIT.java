package com.nnk.springboot.itTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.RatingService;
import com.nnk.springboot.services.UserService;
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
public class RatingIT {

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  RatingService ratingService;

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
  void ratingValidateWorkForAdmin() throws Exception {

    Rating ratingTest = new Rating();
    ratingTest.setFitchRating("fitch");
    ratingTest.setMoodysRating("moodys");
    ratingTest.setSandPRating("sand");
    ratingTest.setOrderNumber(10);

    MvcResult result = this.mockMvc.perform(post("/rating/validate")
            .flashAttr("rating", ratingTest)
            .content(objectMapper.writeValueAsString(ratingTest))
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(302))
            .andExpect(redirectedUrl("/rating/list"))
            .andReturn();
int id = ratingService.findAll().get(0).getId();
    assertEquals(ratingService.findById(id).get().getFitchRating(), "fitch");
    assertEquals(ratingService.findById(id).get().getMoodysRating(), "moodys");
    assertEquals(ratingService.findById(id).get().getSandPRating(), "sand");
    assertEquals(ratingService.findById(id).get().getOrderNumber(), 10);

    assertNull(result.getResponse().getErrorMessage());

    //Deleting user created  for this test using direct call to user Service

    ratingService.delete(ratingService.findById(id).get());

  }





  @Test
  void getRatingList() throws Exception {
    Rating ratingTest = new Rating();
    ratingTest.setFitchRating("fitch");
    ratingTest.setMoodysRating("moodys");
    ratingTest.setSandPRating("sand");
    ratingTest.setOrderNumber(10);
    ratingService.save(ratingTest);
    MvcResult mvcResult = this.mockMvc.perform(get("/rating/list").with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(200))
            .andReturn();

    assertTrue(mvcResult.getResponse().getContentAsString().contains("fitch"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("moodys"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("sand"));
    assertTrue(mvcResult.getResponse().getContentAsString().contains("10"));


  }

  @Test
  void ratingUpdate() throws Exception {
    Rating ratingTest = new Rating();
    ratingTest.setFitchRating("fitch");
    ratingTest.setMoodysRating("moodys");
    ratingTest.setSandPRating("sand");
    ratingTest.setOrderNumber(10);
    ratingService.save(ratingTest);

    int id = ratingService.findAll().get(0).getId();
    Rating updated = ratingService.findById(id).get();
    updated.setFitchRating("feetch");

    MvcResult result = this.mockMvc.perform(post("/rating/update/"+id)
            .flashAttr("rating", updated)
            .content(objectMapper.writeValueAsString(updated))
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(302))
            .andExpect(redirectedUrl("/rating/list"))
            .andReturn();

    assertTrue(ratingService.findById(id).get().getFitchRating().equals("feetch"));
    assertNull(result.getResponse().getErrorMessage());


    // ReSet to original values
    ratingService.delete(updated);

  }



  @Test
  void deleteUser() throws Exception {
    Rating ratingTest = new Rating();
    ratingTest.setFitchRating("fitch");
    ratingTest.setMoodysRating("moodys");
    ratingTest.setSandPRating("sand");
    ratingTest.setOrderNumber(10);
    ratingService.save(ratingTest);

    int id = ratingService.findAll().get(0).getId();


    MvcResult result = this.mockMvc.perform(get("/rating/delete/" + id)
            .with(user("Geff").roles("ADMIN"))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(302))
            .andExpect(redirectedUrl("/rating/list"))
            .andReturn();

    assertFalse(ratingService.findById(id).isPresent());
    assertNull(result.getResponse().getErrorMessage());


  }


}
