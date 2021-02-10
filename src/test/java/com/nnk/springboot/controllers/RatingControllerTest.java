package com.nnk.springboot.controllers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingServiceImpl;
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

@ContextConfiguration(classes = {RatingController.class})
@ExtendWith(SpringExtension.class)
public class RatingControllerTest {
  @Autowired
  private RatingController ratingController;

  @MockBean
  private RatingServiceImpl ratingServiceImpl;

  @Test
  public void testAddRatingForm() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/rating/add");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.ratingController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("rating"))
            .andExpect(MockMvcResultMatchers.view().name("rating/add"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("rating/add"));
  }

  @Test
  public void testAddRatingForm2() throws Exception {
    MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/rating/add");
    getResult.contentType("Not all who wander are lost");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.ratingController).build();
    ResultActions actualPerformResult = buildResult.perform(getResult);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("rating"))
            .andExpect(MockMvcResultMatchers.view().name("rating/add"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("rating/add"));
  }

  @Test
  public void testDeleteRating() throws Exception {
    Rating rating = new Rating();
    rating.setId(1);
    rating.setOrderNumber(10);
    rating.setFitchRating("Fitch Rating");
    rating.setSandPRating("Sand PRating");
    rating.setMoodysRating("Moodys Rating");
    Optional<Rating> ofResult = Optional.<Rating>of(rating);
    doNothing().when(this.ratingServiceImpl).delete((Rating) any());
    when(this.ratingServiceImpl.findById((Integer) any())).thenReturn(ofResult);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/rating/delete/{id}", 1);
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.ratingController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(0))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/rating/list"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/rating/list"));
  }

  @Test
  public void testHome() throws Exception {
    when(this.ratingServiceImpl.findAll()).thenReturn(new ArrayList<Rating>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/rating/list");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.ratingController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("rating"))
            .andExpect(MockMvcResultMatchers.view().name("rating/list"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("rating/list"));
  }

  @Test
  public void testHome2() throws Exception {
    when(this.ratingServiceImpl.findAll()).thenReturn(new ArrayList<Rating>());
    MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/rating/list");
    getResult.contentType("Not all who wander are lost");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.ratingController).build();
    ResultActions actualPerformResult = buildResult.perform(getResult);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("rating"))
            .andExpect(MockMvcResultMatchers.view().name("rating/list"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("rating/list"));
  }

  @Test
  public void testShowUpdateForm() throws Exception {
    Rating rating = new Rating();
    rating.setId(1);
    rating.setOrderNumber(10);
    rating.setFitchRating("Fitch Rating");
    rating.setSandPRating("Sand PRating");
    rating.setMoodysRating("Moodys Rating");
    Optional<Rating> ofResult = Optional.<Rating>of(rating);
    when(this.ratingServiceImpl.findById((Integer) any())).thenReturn(ofResult);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/rating/update/{id}", 1);
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.ratingController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("rating"))
            .andExpect(MockMvcResultMatchers.view().name("rating/update"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("rating/update"));
  }

  @Test
  public void testUpdateRating() throws Exception {
    Rating rating = new Rating();
    rating.setId(1);
    rating.setOrderNumber(10);
    rating.setFitchRating("Fitch Rating");
    rating.setSandPRating("Sand PRating");
    rating.setMoodysRating("Moodys Rating");
    Optional<Rating> ofResult = Optional.<Rating>of(rating);
    doNothing().when(this.ratingServiceImpl).save((Rating) any());
    when(this.ratingServiceImpl.findById((Integer) any())).thenReturn(ofResult);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/rating/update/{id}", 1);
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.ratingController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("rating"))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/rating/list"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/rating/list"));
  }

  @Test
  public void testUpdateRating2() throws Exception {
    doNothing().when(this.ratingServiceImpl).save((Rating) any());
    when(this.ratingServiceImpl.findById((Integer) any())).thenReturn(Optional.<Rating>empty());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/rating/update/{id}", 1);
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.ratingController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("rating"))
            .andExpect(MockMvcResultMatchers.view().name("/rating/list"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("/rating/list"));
  }

  @Test
  public void testUpdateRating3() throws Exception {
    Rating rating = new Rating();
    rating.setId(1);
    rating.setOrderNumber(10);
    rating.setFitchRating("Fitch Rating");
    rating.setSandPRating("Sand PRating");
    rating.setMoodysRating("Moodys Rating");
    Optional<Rating> ofResult = Optional.<Rating>of(rating);
    doNothing().when(this.ratingServiceImpl).save((Rating) any());
    when(this.ratingServiceImpl.findById((Integer) any())).thenReturn(ofResult);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/rating/update/{id}", 1)
            .param("orderNumber", "Order Number");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.ratingController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("rating"))
            .andExpect(MockMvcResultMatchers.view().name("/rating/list"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("/rating/list"));
  }

  @Test
  public void testValidate() throws Exception {
    doNothing().when(this.ratingServiceImpl).save((com.nnk.springboot.domain.Rating) any());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/rating/validate");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.ratingController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("rating"))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/rating/list"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/rating/list"));
  }

  @Test
  public void testValidate2() throws Exception {
    doNothing().when(this.ratingServiceImpl).save((com.nnk.springboot.domain.Rating) any());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/rating/validate")
            .param("orderNumber", "Order Number");
    MockMvc buildResult = MockMvcBuilders.standaloneSetup(this.ratingController).build();
    ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("rating"))
            .andExpect(MockMvcResultMatchers.view().name("rating/list"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("rating/list"));
  }
}

