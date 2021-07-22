package com.smc.stockmarketcharting.services.impls;

import com.smc.stockmarketcharting.controllers.SectorController;
import com.smc.stockmarketcharting.dtos.CompanyDto;
import com.smc.stockmarketcharting.dtos.SectorDto;
import com.smc.stockmarketcharting.mappers.CompanyMapper;
import com.smc.stockmarketcharting.mappers.SectorMapper;
import com.smc.stockmarketcharting.models.Sector;
import com.smc.stockmarketcharting.repositories.SectorRepository;
import com.smc.stockmarketcharting.services.SectorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SectorServiceImpl implements SectorService {

    Logger logger = LoggerFactory.getLogger(SectorServiceImpl.class);

    @Autowired
    SectorMapper sectorMapper;

    @Autowired
    SectorRepository sectorRepository;

    @Autowired
    CompanyMapper companyMapper;

    @Override
    public List<SectorDto> findAll(){
        return new ArrayList<>(sectorMapper.toSectorDtos(sectorRepository.findAll()));
    }

    @Override
    public SectorDto save(SectorDto sectorDto){
        Sector sector = sectorMapper.toSector(sectorDto);
        sector = sectorRepository.save(sector);
        return sectorMapper.toSectorDto(sector);
    }
    @Override
    public SectorDto update(SectorDto sectorDto){
        Optional<Sector> sectorOptional = sectorRepository.findById(sectorDto.getId());
        if(sectorOptional.isPresent()){
            Sector sector = sectorMapper.toSector(sectorDto);
            sector.setCompanies(sectorOptional.get().getCompanies());
            sector = sectorRepository.save(sector);
            return sectorMapper.toSectorDto(sector);
        }
        return null;
    }

    @Override
    public String deleteById(long id){
        try{
            sectorRepository.deleteById(id);
            return "Successfully deleted the sector";
        }
        catch (Exception e){
            throw new RuntimeException("Could not delete sector: "+e.getMessage());
        }
    }

    @Override
    public List<CompanyDto> getAllCompanies(String sectorName) {
        Sector sector = sectorRepository.findSectorByName(sectorName).orElse(null);
        logger.error(sector.getName());
        if(sector != null){
            return companyMapper.toCompanyDtos(sector.getCompanies());
        }
        return null;
    }
}
