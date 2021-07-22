package com.smc.stockmarketcharting.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IpoDto {
    private long id;
    private String companyName;
    private String exchangeName;
    private double pricePerShare;
    private int totalShares;
    private String openDateTime;
    private String remarks;
}
