package com.smc.stockmarketcharting.services.impls;

import com.smc.stockmarketcharting.dtos.CompanyDto;
import com.smc.stockmarketcharting.dtos.CompanyExchangeCodeMappingDto;
import com.smc.stockmarketcharting.mappers.CompanyMapper;
import com.smc.stockmarketcharting.models.Company;
import com.smc.stockmarketcharting.models.CompanyExchangeCode;
import com.smc.stockmarketcharting.models.Sector;
import com.smc.stockmarketcharting.models.StockExchange;
import com.smc.stockmarketcharting.repositories.CompanyExchangeCodeRepository;
import com.smc.stockmarketcharting.repositories.CompanyRepository;
import com.smc.stockmarketcharting.repositories.SectorRepository;
import com.smc.stockmarketcharting.repositories.StockExchangeRepository;
import com.smc.stockmarketcharting.services.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    CompanyExchangeCodeRepository companyExchangeCodeRepository;

    @Autowired
    StockExchangeRepository stockExchangeRepository;

    @Autowired
    SectorRepository sectorRepository;

    @Autowired
    CompanyMapper companyMapper;

    @Override
    public CompanyDto findById(long id){
        return companyRepository.findById(id).map(company ->
                companyMapper.toCompanyDto(company)).orElse(null);
    }

    @Override
    public List<CompanyDto> findAll(){
        return new ArrayList<>(companyMapper.toCompanyDtos(companyRepository.findAll()));
    }

    @Override
    public List<CompanyDto> findMatchingCompanies(String pattern){
        return companyMapper.toCompanyDtos(companyRepository.findCompanyByNameContaining(pattern));
    }

    @Override
    public CompanyDto addCompany(CompanyDto companyDto) {
        Company company = companyMapper.toCompany(companyDto);
        if(companyDto.getSectorName()!=null){
            Sector sector = new Sector();
            sector.setName(companyDto.getSectorName());
            sector.setDescription("Dummy Data about the sector");
            sector = sectorRepository.save(sector);
            company.setSector(sector);
        }
        company = companyRepository.save(company);
        return companyMapper.toCompanyDto(company);
    }

    @Override
    public CompanyDto editCompany(CompanyDto companyDto){
        Optional<Company> companyOptional = companyRepository.findById(companyDto.getId());
        if(!companyOptional.isPresent()){
            return null;
        }
        Company company = companyMapper.toCompany(companyDto);
        company.setSector(companyOptional.get().getSector());
        company = companyRepository.save(company);
        return companyMapper.toCompanyDto(company);
    }

    @Override
    public String deleteById(long id){
        try{
            companyRepository.deleteById(id);
            return "Successfully deleted the company";
        }
        catch (Exception e){
            throw new RuntimeException("Could not delete the company: " + e.getMessage());
        }
    }

    @Override
    public CompanyDto mapCompanyExchangeCode(String companyName,List<CompanyExchangeCodeMappingDto> exchangeCodes) {
        Company company = companyRepository.findCompanyByName(companyName).orElse(null);
        if(company == null){
            return null;
        }
        for(CompanyExchangeCodeMappingDto exchangeMapping:exchangeCodes){
            StockExchange exchange = stockExchangeRepository
                    .findStockExchangeByName(exchangeMapping.getExchangeName()).orElse(null);
            if(exchange!=null){
                CompanyExchangeCode companyExchangeCode = new CompanyExchangeCode();
                companyExchangeCode.setCompany(company);
                companyExchangeCode.setStockExchange(exchange);
                companyExchangeCode.setCompanyCode(exchangeMapping.getCompanyCode());
                companyExchangeCodeRepository.save(companyExchangeCode);
            }
        }
        return companyMapper.toCompanyDto(company);
    }
}
