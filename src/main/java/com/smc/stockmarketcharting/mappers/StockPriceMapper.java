package com.smc.stockmarketcharting.mappers;

import com.smc.stockmarketcharting.dtos.StockPriceDto;
import com.smc.stockmarketcharting.models.StockExchange;
import com.smc.stockmarketcharting.models.StockPrice;
import com.smc.stockmarketcharting.repositories.CompanyExchangeCodeRepository;
import com.smc.stockmarketcharting.repositories.StockExchangeRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class StockPriceMapper {

    @Autowired
    CompanyExchangeCodeRepository companyExchangeCodeRepository;

    @Autowired
    StockExchangeRepository stockExchangeRepository;

    public StockPriceDto toStockPriceDto(StockPrice stockPrice){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        StockPriceDto stockPriceDto = mapper.map(stockPrice,StockPriceDto.class);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        stockPriceDto.setDate(dateFormat.format(stockPrice.getDate()));
        stockPriceDto.setTime(stockPrice.getTime().toString());
        StockExchange stockExchange = stockExchangeRepository.findStockExchangeByName(stockPrice.getStockExchangeName())
                .orElse(null);
        stockPriceDto.setCompanyCode(
                companyExchangeCodeRepository.findCompanyExchangeCodeByCompanyAndStockExchange(
                        stockPrice.getCompany(), stockExchange
                ).getCompanyCode());
        return stockPriceDto;
    }

    public StockPrice toStockPrice(StockPriceDto stockPriceDto){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        StockPrice stockPrice = mapper.map(stockPriceDto,StockPrice.class);
        try {
            stockPrice.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(stockPriceDto.getDate()));
            stockPrice.setTime(LocalTime.parse(stockPriceDto.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return stockPrice;
    }

    public List<StockPriceDto> toStockPriceDtos(List<StockPrice> stockPrices){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<StockPriceDto> stockPriceDtos = new ArrayList<>();
        for(StockPrice stockPrice:stockPrices){
            stockPriceDtos.add(toStockPriceDto(stockPrice));
        }
        return stockPriceDtos;
    }
}
