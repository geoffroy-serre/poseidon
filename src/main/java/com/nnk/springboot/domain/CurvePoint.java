package com.nnk.springboot.domain;

import java.sql.Timestamp;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "curve_point")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurvePoint {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "curve_id")
  @NotNull(message = "must not be null")
  private Integer curveId;

  @Column(name = "as_of_date")
  @DateTimeFormat(pattern = "MM/dd/yyyy")
  private Timestamp asOfDate;

  @Column(name = "term")
  private Double term;

  @Column(name = "value")
  private Double value;

  @Column(name = "creation_date")
  @DateTimeFormat(pattern = "MM/dd/yyyy")
  private Timestamp creationDate;

  public CurvePoint(int curveId, double term, double value) {
    this.curveId = curveId;
    this.term = term;
    this.value = value;
  }
}
