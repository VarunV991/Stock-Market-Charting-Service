package com.smc.stockmarketcharting.mappers;

import com.smc.stockmarketcharting.dtos.CompanyDto;
import com.smc.stockmarketcharting.dtos.StockExchangeDto;
import com.smc.stockmarketcharting.models.Company;
import com.smc.stockmarketcharting.models.StockExchange;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class StockExchangeMapper {

    public StockExchangeDto toStockExchangeDto(StockExchange stockExchange){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        StockExchangeDto stockExchangeDto = mapper.map(stockExchange,StockExchangeDto.class);
        return stockExchangeDto;
    }

    public StockExchange toStockExchange(StockExchangeDto stockExchangeDto){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        StockExchange stockExchange = mapper.map(stockExchangeDto,StockExchange.class);
        return stockExchange;
    }

    public List<StockExchangeDto> toStockExchangeDtos(List<StockExchange> stockExchanges){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<StockExchangeDto> stockExchangeDtos = Arrays.asList(
                mapper.map(stockExchanges, StockExchangeDto[].class));
        return stockExchangeDtos;
    }
}
