package com.nnk.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  PasswordEncoder passwordEncoder;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.httpBasic().disable();
    /*http.csrf().disable().authorizeRequests()
            .antMatchers("/*").permitAll()
            .anyRequest().authenticated();*/


    //hasROle("admin")
  }

  @Override
  protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
            .withUser("FrontEnd").password(passwordEncoder.encode("7LS`kFLzk})u^wr")).roles(
            "ADMIN");


  }

}
