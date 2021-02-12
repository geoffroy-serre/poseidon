package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public void save(User user) {
    userRepository.save(user);
  }

  @Override
  public void delete(User user) {
    userRepository.delete(user);
  }

  @Override
  public Optional<User> findById(Integer id) {
    return userRepository.findById(id);
  }

  @Override
  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public void deleteById(Integer id) {
    userRepository.deleteById(id);
  }
}
