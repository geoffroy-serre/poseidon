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
@Table(name = "bid_list")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BidList {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "account")
  @NotBlank(message="Account is mandatory")
  @Size(max = 30)
  private String account;

  @Column(name = "type")
  @Size(max = 30)
  @NotBlank(message="Type is mandatory")
  private String type;

  @Column(name = "bid_quantity")
  private Double bidQuantity;

  @Column(name = "ask_quantity")
  private Double askQuantity;

  @Column(name = "bid")
  private Double bid;

  @Column(name = "ask")
  private Double ask;

  @Column(name = "benchmark")
  @Size(max = 125)
  private String benchmark;

  @Column(name = "bid_list_date")
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

  @Column(name = "creation_name")
  @Size(max = 125)
  private String creationName;

  @Column(name = "creation_date")
  @DateTimeFormat(pattern = "MM/dd/yyyy")
  private Timestamp creationDate;

  @Column(name = "revision_name")
  @Size(max = 125)
  private String revisionName;

  @Column(name = "revision_date")
  @DateTimeFormat(pattern = "MM/dd/yyyy")
  private Timestamp revisionDate;

  @Column(name = "deal_name")
  @Size(max = 125)
  private String dealName;

  @Column(name = "deal_type")
  @Size(max = 125)
  private String dealType;

  @Column(name = "source_list_id")
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
