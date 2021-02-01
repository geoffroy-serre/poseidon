package com.nnk.springboot.domain;

import java.sql.Timestamp;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;


import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bidlist")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BidList {
  // TODO: Map columns in data table BIDLIST with corresponding java fields

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "BidListId")
  private Integer BidListId;

  @Column(name = "account")
  @NotBlank(message="Account can't be empty")
  @Size(max = 30)
  private String account;

  @Column(name = "type")
  @Size(max = 30)
  @NotBlank(message="Type can't be empty")
  private String type;

  @Column(name = "bidQuantity")
  private Double bidQuantity;

  @Column(name = "askQuantity")
  private Double askQuantity;

  @Column(name = "bid")
  private Double bid;

  @Column(name = "ask")
  private Double ask;

  @Column(name = "benchmark")
  @Size(max = 125)
  private String benchmark;

  @Column(name = "bidListDate")
  @DateTimeFormat(pattern = "MM/dd/yyyy")
  private Timestamp bidListDate;

  @Column(name = "commentary")
  @Size(max = 125)
  private String commentary;

  @Column(name = "security")
  @Size(max = 125)
  private String security;

  @Column(name = "status")
  @Size(max = 10)
  private String status;

  @Column(name = "trader")
  @Size(max = 125)
  private String trader;

  @Column(name = "book")
  @Size(max = 125)
  private String book;

  @Column(name = "creationName")
  @Size(max = 125)
  private String creationName;

  @Column(name = "creationDate")
  @DateTimeFormat(pattern = "MM/dd/yyyy")
  private Timestamp creationDate;

  @Column(name = "revisionName")
  @Size(max = 125)
  private String revisionName;

  @Column(name = "revisionDate")
  @DateTimeFormat(pattern = "MM/dd/yyyy")
  private Timestamp revisionDate;

  @Column(name = "dealName")
  @Size(max = 125)
  private String dealName;

  @Column(name = "dealType")
  @Size(max = 125)
  private String dealType;

  @Column(name = "sourceListId")
  @Size(max = 125)
  private String sourceListId;

  @Column(name = "side")
  @Size(max = 125)
  private String side;

  public BidList(String account, String type, double bidQuantity) {
    this.account = account;
    this.type = type;
    this.bidQuantity = bidQuantity;

  }
}
