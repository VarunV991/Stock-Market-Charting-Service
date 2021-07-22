package com.smc.stockmarketcharting.services.impls;

import com.smc.stockmarketcharting.dtos.CompanyDto;
import com.smc.stockmarketcharting.dtos.CompanyExchangeCodeMappingDto;
import com.smc.stockmarketcharting.dtos.IpoDto;
import com.smc.stockmarketcharting.mappers.CompanyMapper;
import com.smc.stockmarketcharting.mappers.IpoMapper;
import com.smc.stockmarketcharting.models.*;
import com.smc.stockmarketcharting.repositories.*;
import com.smc.stockmarketcharting.services.CompanyService;
import com.smc.stockmarketcharting.services.SectorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    IpoRepository ipoRepository;

    @Autowired
    CompanyMapper companyMapper;

    @Autowired
    IpoMapper ipoMapper;

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
        if(companyDto.getSectorName() != null){
            Sector sector = sectorRepository.findSectorByName(companyDto.getSectorName())
                    .orElse(null);
            if(sector != null){
                company.setSector(sector);
                sector.getCompanies().add(company);
                company = companyRepository.save(company);
                return companyMapper.toCompanyDto(company);
            }
        }
        return null;
    }

    @Override
    public CompanyDto editCompany(CompanyDto companyDto){
        Optional<Company> companyOptional = companyRepository.findById(companyDto.getId());
        if(!companyOptional.isPresent()){
            return null;
        }
        Company company = companyMapper.toCompany(companyDto);
        if(companyDto.getSectorName() != null){
            company.setSector(sectorRepository.findSectorByName(companyDto.getSectorName())
                    .orElse(null));
        }
        else {
            company.setSector(null);
        }
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
    public String mapCompanyExchangeCode(String companyName,List<CompanyExchangeCodeMappingDto> exchangeCodes) {
        Company company = companyRepository.findCompanyByName(companyName).orElse(null);
        int count = 0;
        if(company != null) {
            for (CompanyExchangeCodeMappingDto exchangeMapping : exchangeCodes) {
                StockExchange exchange = stockExchangeRepository
                        .findStockExchangeByName(exchangeMapping.getExchangeName()).orElse(null);
                if (exchange != null) {
                    CompanyExchangeCode companyExchangeCode = new CompanyExchangeCode();
                    companyExchangeCode.setCompany(company);
                    companyExchangeCode.setStockExchange(exchange);
                    companyExchangeCode.setCompanyCode(exchangeMapping.getCompanyCode());
                    companyExchangeCodeRepository.save(companyExchangeCode);
                    count+=1;
                }
            }
        }
        if(count == exchangeCodes.size()){
            return "Successfully mapped all the Company Exchange Codes";
        }
        else if(count!=0){
            throw new RuntimeException("Some exchange code mappings have invalid Exchange Name. " +
                    "Please upload valid mappings");
        }
        return null;
    }

    @Override
    public IpoDto getCompanyIpoDetails(String companyName){
        Company company = companyRepository.findCompanyByName(companyName).orElse(null);
        if(company == null){
            throw new RuntimeException("Could not find company with name: "+companyName);
        }
        Ipo ipo = ipoRepository.getIpoByCompany(company).orElse(null);
        if(ipo == null){
            throw new RuntimeException("Could not find ipo with company name: "+companyName);
        }
        return ipoMapper.toIpoDto(ipo);
    }
}
