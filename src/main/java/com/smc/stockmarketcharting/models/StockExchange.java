package com.smc.stockmarketcharting.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "StockExchange")
public class StockExchange {

    @GeneratedValue
    @Id
    private long id;
    private String name;
    private String description;
    private String address;
    private String remarks;

    @OneToMany(targetEntity = CompanyExchangeCode.class)
    private List<CompanyExchangeCode> companyExchangeCodes;

    @OneToMany(targetEntity = StockPrice.class)
    private List<StockPrice> stockPrices;
}
