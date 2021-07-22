package com.smc.stockmarketcharting.services;

import com.smc.stockmarketcharting.dtos.CompanyDto;
import com.smc.stockmarketcharting.dtos.StockExchangeDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StockExchangeService {

    public StockExchangeDto save(StockExchangeDto stockExchangeDto);
    public StockExchangeDto update(StockExchangeDto stockExchangeDto);
    public List<StockExchangeDto> findAll();
    public List<CompanyDto> getAllCompanies(String stockExchangeName);
}
