package com.nnk.springboot.domain;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;


  @Column(name = "username")
  @NotBlank(message = "Username can't be empty")
  private String username;

  @NotBlank(message = "Password can't be empty")
  @Column(name = "password")
  private String password;

  @NotBlank(message = "Full Name can't be empty")
  @Column(name = "fullname")
  private String fullname;

  @NotBlank(message = "Role must be selected")
  @Column(name = "role")
  private String role;


}
