package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Use Jpa
 * List<User> findAll();
 * <p>
 * void save(User user);
 * <p>
 * void delete(User user);
 * <p>
 * Optional<User> findById(Integer id);
 * <p>
 * User findByUsername(String username);
 * <p>
 * void deleteById(Integer id);
 */
@Service
public interface UserService {

  List<User> findAll();

  void save(User user);

  void delete(User user);

  Optional<User> findById(Integer id);

  User findByUsername(String username);

  void deleteById(Integer id);
}
