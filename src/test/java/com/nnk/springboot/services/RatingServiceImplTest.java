package com.nnk.springboot.services;


import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RatingServiceImplTest {

  @Mock
  RatingRepository ratingRepository;

  @InjectMocks
  RatingService ratingService = new RatingServiceImpl();

  Rating rating = new Rating();

  @Test
  void findAll() {
    List<Rating> ratings = new ArrayList<>();
    when(ratingRepository.findAll()).thenReturn(ratings);
    ratingService.findAll();
    Mockito.verify(ratingRepository, Mockito.times(1)).findAll();
    assertEquals(ratingService.findAll(), ratings);
  }

  @Test
  void save() {

    when(ratingRepository.save(rating)).thenReturn(rating);
    ratingService.save(rating);
    Mockito.verify(ratingRepository, Mockito.times(1)).save(rating);
    assertDoesNotThrow(() -> ratingService.save(rating));
  }

  @Test
  void findById() {

    rating.setId(1);
    Optional<Rating> ratings = Optional.of(rating);

    when(ratingRepository.findById(1)).thenReturn(ratings);
    ratingService.findById(1);
    Mockito.verify(ratingRepository, Mockito.times(1)).findById(1);
    assertEquals(ratingService.findById(1), ratings);

  }

  @Test
  void delete() {
    ratingService.delete(rating);
    Mockito.verify(ratingRepository, Mockito.times(1)).delete(rating);
    assertDoesNotThrow(() -> ratingService.delete(rating));
  }
}
