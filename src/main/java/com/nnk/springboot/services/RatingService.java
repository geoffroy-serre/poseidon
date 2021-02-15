package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Use Jpa:
 * List<Rating> findAll();
 * <p>
 * void save(Rating rating);
 * <p>
 * Optional<Rating> findById(Integer id);
 * <p>
 * void delete(Rating rating);
 */
@Service
public interface RatingService {
  List<Rating> findAll();

  void save(Rating rating);

  Optional<Rating> findById(Integer id);

  void delete(Rating rating);
}
