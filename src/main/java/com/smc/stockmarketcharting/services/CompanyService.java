package com.smc.stockmarketcharting.services;

import com.smc.stockmarketcharting.dtos.CompanyDto;
import com.smc.stockmarketcharting.dtos.CompanyExchangeCodeMappingDto;
import com.smc.stockmarketcharting.dtos.IpoDto;
import com.smc.stockmarketcharting.dtos.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CompanyService {
    public CompanyDto findById(long id);
    public List<CompanyDto> findAll();
    public List<CompanyDto> findMatchingCompanies(String pattern);
    public CompanyDto addCompany(CompanyDto companyDto);
    public CompanyDto editCompany(CompanyDto companyDto);
    public String deleteById(long id);
    public String  mapCompanyExchangeCode(String companyName,
                                             List<CompanyExchangeCodeMappingDto> exchangeCodes);
    public IpoDto getCompanyIpoDetails(String companyName);
}
