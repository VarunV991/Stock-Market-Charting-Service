package com.smc.stockmarketcharting.mappers;

import com.smc.stockmarketcharting.dtos.CompanyDto;
import com.smc.stockmarketcharting.models.Company;
import com.smc.stockmarketcharting.models.CompanyExchangeCode;
import com.smc.stockmarketcharting.repositories.CompanyExchangeCodeRepository;
import com.smc.stockmarketcharting.repositories.SectorRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CompanyMapper {

    @Autowired
    SectorRepository sectorRepository;

    @Autowired
    CompanyExchangeCodeRepository exchangeCodeRepository;

    public CompanyDto toCompanyDto(Company company){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CompanyDto companyDto = mapper.map(company,CompanyDto.class);
        if(company.getSector()!=null){
            companyDto.setSectorName(company.getSector().getName());
        }
        List<CompanyExchangeCode> exchangeCodeList = exchangeCodeRepository
                .findCompanyExchangeCodesByCompany(company);
        String exchangeCodes = "";
        for (CompanyExchangeCode exchangeCode:exchangeCodeList){
            exchangeCodes += exchangeCode.getStockExchange().getName() + ",";
        }
        if(exchangeCodes.length()>0){
            exchangeCodes = exchangeCodes.substring(0,exchangeCodes.length()-1);
            companyDto.setExchanges(exchangeCodes);
        }
        return companyDto;
    }

    public Company toCompany(CompanyDto companyDto){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Company company = mapper.map(companyDto,Company.class);
        return company;
    }

    public List<CompanyDto> toCompanyDtos(List<Company> company){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<CompanyDto> companyDtoList = new ArrayList<>();
        for(Company companyDetails:company){
            companyDtoList.add(toCompanyDto(companyDetails));
        }
        return companyDtoList;
    }
}
