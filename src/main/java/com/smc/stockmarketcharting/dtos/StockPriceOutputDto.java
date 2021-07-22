package com.smc.stockmarketcharting.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockPriceOutputDto {
    private String date;
    private float price;
    private int noOfEntries;
}
