package com.smc.stockmarketcharting.services.impls;

import com.smc.stockmarketcharting.dtos.CompanyDto;
import com.smc.stockmarketcharting.dtos.StockExchangeDto;
import com.smc.stockmarketcharting.mappers.CompanyMapper;
import com.smc.stockmarketcharting.mappers.StockExchangeMapper;
import com.smc.stockmarketcharting.models.CompanyExchangeCode;
import com.smc.stockmarketcharting.models.StockExchange;
import com.smc.stockmarketcharting.repositories.CompanyExchangeCodeRepository;
import com.smc.stockmarketcharting.repositories.StockExchangeRepository;
import com.smc.stockmarketcharting.services.StockExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockExchangeServiceImpl implements StockExchangeService {

    @Autowired
    StockExchangeRepository stockExchangeRepository;

    @Autowired
    StockExchangeMapper stockExchangeMapper;

    @Autowired
    CompanyExchangeCodeRepository companyExchangeCodeRepository;

    @Autowired
    CompanyMapper companyMapper;

    @Override
    public StockExchangeDto save(StockExchangeDto stockExchangeDto) {
        StockExchange stockExchange = stockExchangeMapper.toStockExchange(stockExchangeDto);
        stockExchange = stockExchangeRepository.save(stockExchange);
        return stockExchangeMapper.toStockExchangeDto(stockExchange);
    }

    @Override
    public StockExchangeDto update(StockExchangeDto stockExchangeDto){
        Optional<StockExchange> stockExchangeOptional =
                stockExchangeRepository.findById(stockExchangeDto.getId());
        if(stockExchangeOptional.isPresent()){
            StockExchange stockExchange = stockExchangeMapper.toStockExchange(stockExchangeDto);
            stockExchange = stockExchangeRepository.save(stockExchange);
            return stockExchangeMapper.toStockExchangeDto(stockExchange);
        }
        return  null;
    }

    @Override
    public List<StockExchangeDto> findAll() {
        return new ArrayList<>(stockExchangeMapper.toStockExchangeDtos(
                stockExchangeRepository.findAll()
        ));
    }

    @Override
    public List<CompanyDto> getAllCompanies(String stockExchangeName) {
        StockExchange stockExchange =
                stockExchangeRepository.findStockExchangeByName(stockExchangeName).orElse(null);
        if(stockExchange!=null){
            List<CompanyDto> companyDtos = new ArrayList<>();
            List<CompanyExchangeCode> companyExchangeCodes =
                    companyExchangeCodeRepository.findCompanyExchangeCodesByStockExchange(stockExchange);
            for(CompanyExchangeCode companyExchangeCode: companyExchangeCodes){
                CompanyDto companyDto = companyMapper.toCompanyDto(companyExchangeCode.getCompany());
                companyDto.setExchangeCode(companyExchangeCode.getCompanyCode());
                companyDtos.add(companyDto);
            }
            return companyDtos;
        }
        return null;
    }
}
