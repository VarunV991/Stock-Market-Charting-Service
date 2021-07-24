package com.smc.stockmarketcharting.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Ipo")
public class Ipo {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private String exchangeName;
    private double pricePerShare;
    private int totalShares;
    private String openDateTime;
    private String remarks;

    @OneToOne(targetEntity = Company.class)
    private Company company;

}
