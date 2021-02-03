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


  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "moodys_rating")
  @Size(max = 125)
  private String moodysRating;

  @Column(name = "sand_p_rating")
  @Size(max = 125)
  private String sandPRating;

  @Column(name = "fitch_rating")
  @Size(max = 125)
  private String fitchRating;

  @Column(name = "order_number")
  private Integer orderNumber;

  public Rating(String moodysRating, String sandPRating, String fitchRating, int orderNumber) {
    this.moodysRating = moodysRating;
    this.sandPRating = sandPRating;
    this.fitchRating = fitchRating;
    this.orderNumber = orderNumber;
  }
}
