package com.smc.stockmarketcharting.services;

import com.smc.stockmarketcharting.dtos.StockPriceDto;
import com.smc.stockmarketcharting.dtos.StockPriceOutputDto;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public interface StockPriceService {
    public List<StockPriceDto> findAll();
    public String deleteById(long id);
    public StockPriceDto save(StockPriceDto stockPriceDto);
    public List<StockPriceDto> saveList(List<StockPriceDto> stockPriceDtos);
    List<StockPriceOutputDto> getStockPricesForCompanyComparison(
            long id,String exchangeName,String fromDate,String toDate,String periodicity);
    List<StockPriceOutputDto> getStockPricesForSectorComparison(
            long id,String exchangeName,String fromDate,String toDate,String periodicity);
}
