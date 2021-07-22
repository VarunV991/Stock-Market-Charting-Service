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

    public StockExchange(String name,String description,String address,String remarks){
        this.name = name;
        this.description = description;
        this.remarks = remarks;
        this.address = address;
    }
}
