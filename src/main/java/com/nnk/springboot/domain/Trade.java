package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "trade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trade {
    // TODO: Map columns in data table TRADE with corresponding java fields
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer tradeId;

  @Column(name = "account")
  @Size(max=30)
  private String account;

  @Column(name = "type")
  @Size(max=30)
  private String type;

  @Column(name = "buyQuantity")
  private Double buyQuantity;

  @Column(name = "sellQuantity")
  private Double sellQuantity;

  @Column(name = "buyPrice")
  private Double buyPrice;

  @Column(name = "sellPrice")
  private Double sellPrice;

  @Column(name = "benchmark")
  @Size(max=125)
  private String benchmark;

  @Column(name = "tradeDate")
  @DateTimeFormat(pattern = "MM/dd/yyyy")
  private Timestamp tradeDate;

  @Column(name = "security")
  @Size(max=125)
  private String security;

  @Column(name = "status")
  @Size(max=10)
  private String status;

  @Column(name = "trader")
  @Size(max=125)
  private String trader;

  @Column(name = "book")
  @Size(max=125)
  private String book;

  @Column(name = "creationName")
  @Size(max=125)
  private String creationName;

  @Column(name = "creationDate")
  @DateTimeFormat(pattern = "MM/dd/yyyy")
  private Timestamp creationDate;

  @Column(name = "revisionName")
  @Size(max=125)
  private String revisionName;

  @Column(name = "revisionDate")
  @DateTimeFormat(pattern = "MM/dd/yyyy")
  private  Timestamp revisionDate;

  @Column(name = "dealName")
  @Size(max=125)
  private String dealName;

  @Column(name = "dealType")
  @Size(max=125)
  private  String dealType;

  @Column(name = "sourceListId")
  @Size(max=125)
  private String sourceListId;

  @Column(name = "side")
  @Size(max=125)
  private String side;

  public Trade(String account, String type) {
    this.account = account;
    this.type = type;
  }
}
