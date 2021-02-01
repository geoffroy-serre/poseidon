package com.nnk.springboot.domain;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Username can't be empty")
    @Column(name ="username")
    private String username;

    @NotBlank(message = "Username can't be empty")
    @Column(name ="password")
    private String password;

    @NotBlank(message = "Username can't be empty")
    @Column(name ="fullname")
    private String fullname;

    @NotBlank(message = "Username can't be empty")
    @Column(name ="role")
    private String role;


}
