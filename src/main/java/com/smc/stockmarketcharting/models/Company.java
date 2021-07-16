package com.smc.stockmarketcharting.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Company")
public class Company {

    @GeneratedValue
    @Id
    private long id;
    private String name;
    private String turnover;
    private String ceo;
    private String description;
    private String boardOfDirectors;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sector_id",nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Sector sector;

    @OneToOne(targetEntity = Ipo.class)
    private Ipo ipo;

    @OneToMany(targetEntity = StockPrice.class)
    private List<StockPrice> stockPrices;
}
