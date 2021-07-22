package com.smc.stockmarketcharting.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockPriceDto {
    private long id;
    private float price;
    private String date;
    private String time;
    private String stockExchangeName;
    private String companyCode;
}
