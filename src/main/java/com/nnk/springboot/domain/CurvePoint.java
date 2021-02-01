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
@Table(name = "curvepoint")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurvePoint {
  // TODO: Map columns in data table CURVEPOINT with corresponding java fields

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "CurveId")
  @NotNull
  private Integer curveId;

  @Column(name = "asOfDate")
  @DateTimeFormat(pattern = "MM/dd/yyyy")
  private Timestamp asOfDate;

  @Column(name = "term")
  private Double term;

  @Column(name = "value")
  private Double value;

  @Column(name = "creationDate")
  @DateTimeFormat(pattern = "MM/dd/yyyy")
  private Timestamp creationDate;

  public CurvePoint(int curveId, double term, double value) {
    this.curveId = curveId;
    this.term = term;
    this.value = value;
  }
}
