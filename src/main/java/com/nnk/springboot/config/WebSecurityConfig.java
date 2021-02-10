package com.nnk.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Qualifier("userDetailsServiceImpl")
  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http
            /*
             * Access setup
             */
            .authorizeRequests()
            .antMatchers("/css/**", "/js/**", "/", "/login").permitAll()
            .antMatchers("/user/validate").hasRole("ADMIN")
            .anyRequest().authenticated()

            /*
             * Login setup
             */
            .and()
            .formLogin()
            .loginPage("/")
            .loginProcessingUrl("/login")
            .defaultSuccessUrl("/bidList/list", true)
            .permitAll()

            /*
             * Logout setup
             */
            .and()
            .logout()
            .logoutSuccessUrl("/")
            .permitAll();
  }

  @Override
  protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());

  }

  @Bean
  public AuthenticationManager customAuthenticationManager() throws Exception {
    return authenticationManager();
  }


}
