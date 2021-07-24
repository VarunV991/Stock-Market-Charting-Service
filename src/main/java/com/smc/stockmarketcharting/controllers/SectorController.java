package com.smc.stockmarketcharting.controllers;

import com.smc.stockmarketcharting.dtos.CompanyDto;
import com.smc.stockmarketcharting.dtos.SectorDto;
import com.smc.stockmarketcharting.services.SectorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/sector")
public class SectorController {

    Logger logger = LoggerFactory.getLogger(SectorController.class);

    @Autowired
    SectorService sectorService;

    @GetMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<SectorDto>> findAll(){
        return ResponseEntity.ok(sectorService.findAll());
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SectorDto> save(@RequestBody SectorDto sectorDto){
        SectorDto sector = sectorService.save(sectorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(sector);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> update(@RequestBody SectorDto sectorDto){
        SectorDto sector = sectorService.update(sectorDto);
        if(sector == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Could not find sector with id: "+sectorDto.getId());
        }
        return ResponseEntity.ok(sector);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteById(@PathVariable long id){
        try {
            String message = sectorService.deleteById(id);
            return ResponseEntity.ok(message);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{sectorName}/companies")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Object> getAllCompanies(@PathVariable String sectorName){
        List<CompanyDto> companyDtos = sectorService.getAllCompanies(sectorName);
        logger.debug(Integer.toString(companyDtos.size()));
        if(companyDtos == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "Could not find sector with name: "+sectorName
            );
        }
        return ResponseEntity.ok(companyDtos);
    }
}
