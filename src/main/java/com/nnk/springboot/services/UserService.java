package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

  List<User> findAll();
  void save(User user);
  void delete(User user);
  Optional<User> findById(Integer id);
  User findByUsername(String username);
}
