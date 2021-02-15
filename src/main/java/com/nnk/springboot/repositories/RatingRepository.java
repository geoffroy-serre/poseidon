package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * No method declared. Extends JpaRepository<Rating, Integer>
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
