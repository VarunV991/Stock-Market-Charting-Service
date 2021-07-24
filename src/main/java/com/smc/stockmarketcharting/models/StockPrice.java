package com.smc.stockmarketcharting.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "StockPrice")
public class StockPrice {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private float price;
    private Date date;
    private LocalTime time;
    private String stockExchangeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Company company;

}
