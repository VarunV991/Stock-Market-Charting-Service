package com.smc.stockmarketcharting.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "StockPrice")
public class StockPrice {

    @GeneratedValue
    @Id
    private long id;
    private double price;
    private String date;
    private String time;

    @ManyToOne(fetch = FetchType.LAZY)
    private StockExchange stockExchange;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;


}
