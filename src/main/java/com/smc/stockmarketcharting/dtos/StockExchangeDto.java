package com.smc.stockmarketcharting.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockExchangeDto {
    private long id;
    private String name;
    private String description;
    private String address;
    private String remarks;
}
