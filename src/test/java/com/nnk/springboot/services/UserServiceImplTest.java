package com.nnk.springboot.services;


import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.repositories.UserRepository;
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
class UserServiceImplTest {

  @Mock
  UserRepository userRepository;

  @InjectMocks
  UserService userService = new UserServiceImpl();

  User user = new User();

  @Test
  void findAll() {
    List<User> users = new ArrayList<>();
    when(userRepository.findAll()).thenReturn(users);
    userService.findAll();
    Mockito.verify(userRepository, Mockito.times(1)).findAll();
    assertEquals(userService.findAll(), users);
  }

  @Test
  void save() {

    when(userRepository.save(user)).thenReturn(user);
    userService.save(user);
    Mockito.verify(userRepository, Mockito.times(1)).save(user);
    assertDoesNotThrow(() -> userService.save(user));
  }

  @Test
  void findById() {

    user.setId(1);
    Optional<User> users = Optional.of(user);

    when(userRepository.findById(1)).thenReturn(users);
    userService.findById(1);
    Mockito.verify(userRepository, Mockito.times(1)).findById(1);
    assertEquals(userService.findById(1), users);

  }

  @Test
  void delete() {
    userService.delete(user);
    Mockito.verify(userRepository, Mockito.times(1)).delete(user);
    assertDoesNotThrow(() -> userService.delete(user));
  }

  @Test
  void findByUsername() {
    when(userRepository.findByUsername("TestUser")).thenReturn(user);
    userService.findByUsername("TestUser");
    Mockito.verify(userRepository, Mockito.times(1)).findByUsername("TestUser");
    assertEquals(userService.findByUsername("TestUser"), user);

  }
}
