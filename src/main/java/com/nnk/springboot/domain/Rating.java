package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

@Entity
@Table(name = "rating")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    // TODO: Map columns in data table RATING with corresponding java fields

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "moodysRating")
  @Size(max = 125)
  private String moodysRating;

  @Column(name = "sandPRating")
  @Size(max = 125)
  private String sandPRating;

  @Column(name = "fitchRating")
  @Size(max = 125)
  private String fitchRating;

  @Column(name = "orderNumber")
  private Integer orderNumber;

  public Rating(String moodysRating, String sandPRating, String fitchRating, int orderNumber) {
    this.moodysRating = moodysRating;
    this.sandPRating = sandPRating;
    this.fitchRating = fitchRating;
    this.orderNumber = orderNumber;
  }
}
