package com.smc.stockmarketcharting.services;

import com.smc.stockmarketcharting.dtos.CompanyDto;
import com.smc.stockmarketcharting.dtos.SectorDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SectorService {

    public List<SectorDto> findAll();
    public SectorDto save(SectorDto sectorDto);
    public SectorDto update(SectorDto sectorDto);
    public String deleteById(long id);
    public List<CompanyDto> getAllCompanies(String sectorName);
}
