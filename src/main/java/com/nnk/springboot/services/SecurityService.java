package com.nnk.springboot.services;

import org.springframework.stereotype.Service;

@Service
public interface SecurityService {
  boolean isAuthenticated();
  void autoLogin(String username, String password);
}
