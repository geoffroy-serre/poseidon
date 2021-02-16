package com.nnk.springboot.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


/**
 * @inheritDoc
 */
@Service
public class SecurityServiceImpl implements SecurityService {

  private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);
  @Autowired
  AuthenticationManager authenticationManager;

  UserDetailsService userDetailsService = new UserDetailsServiceImpl();

  @Override
  public boolean isAuthenticated() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
      return false;
    }
    return authentication.isAuthenticated();
  }

  @Override
  public void autoLogin(String username, String password) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(userDetails, password,
                    userDetails.getAuthorities());

    authenticationManager.authenticate(usernamePasswordAuthenticationToken);

    if (usernamePasswordAuthenticationToken.isAuthenticated()) {
      SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      logger.debug(String.format("Auto login %s successfully!", username));
    }
  }
}
