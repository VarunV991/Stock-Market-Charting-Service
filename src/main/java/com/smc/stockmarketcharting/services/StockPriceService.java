package com.smc.stockmarketcharting.services;

import com.smc.stockmarketcharting.dtos.StockPriceDto;
import com.smc.stockmarketcharting.dtos.StockPriceOutputDto;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Service
public interface StockPriceService {
    public List<StockPriceDto> findAll();
    public String deleteById(long id);
    public StockPriceDto save(StockPriceDto stockPriceDto);
    public List<StockPriceDto> saveList(List<StockPriceDto> stockPriceDtos);
    Map<String,Double> getStockPricesForCompanyComparison(
            long id,String exchangeName,String fromDate,String toDate,String periodicity);
    Map<String,Double> getStockPricesForSectorComparison(
            long id,String exchangeName,String fromDate,String toDate,String periodicity);
    List<StockPriceDto> getStockPricesForCompany(String name);
}
