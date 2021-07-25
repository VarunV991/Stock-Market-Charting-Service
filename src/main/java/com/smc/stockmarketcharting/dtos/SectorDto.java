package com.smc.stockmarketcharting.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectorDto {
    private long id;
    private String name;
    private String description;

    public SectorDto(String name, String description){
        this.name = name;
        this.description = description;
    }
}
