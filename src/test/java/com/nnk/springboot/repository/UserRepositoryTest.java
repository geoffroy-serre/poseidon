package com.nnk.springboot.repository;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  public void userTest() {
    User user = new User(15,"Geff","pwd","fullName","ADMIN");

    // Save
    user = userRepository.save(user);
    Assertions.assertNotNull(user.getId());
    Assertions.assertTrue(user.getUsername().equals("Geff"));

    // Update
    user.setFullname("fulleNameAgain");
    user = userRepository.save(user);
    Assertions.assertTrue(user.getFullname().equals("fulleNameAgain"));

    // Find
    List<User> userResult = userRepository.findAll();
    Assertions.assertTrue(userResult.size() > 0);

    User userResult2 = userRepository.findByUsername("Geff");
    Assertions.assertTrue(userResult.size() > 0);


    // Delete
    Integer id = user.getId();
    userRepository.delete(user);
    Optional<User> userList = userRepository.findById(id);
    Assertions.assertFalse(userList.isPresent());
  }
}
