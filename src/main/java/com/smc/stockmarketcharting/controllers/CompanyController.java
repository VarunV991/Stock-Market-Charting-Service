package com.smc.stockmarketcharting.controllers;

import com.smc.stockmarketcharting.dtos.CompanyDto;
import com.smc.stockmarketcharting.dtos.CompanyExchangeCodeMappingDto;
import com.smc.stockmarketcharting.dtos.IpoDto;
import com.smc.stockmarketcharting.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Object> findById(@PathVariable long id){
        CompanyDto companyDto = companyService.findById(id);
        if(companyDto == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Could not find the company with id: "+id);
        }
        return ResponseEntity.ok(companyDto);
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<CompanyDto>> findAll(){
        List<CompanyDto> companyDtoList = companyService.findAll();
        return ResponseEntity.ok(companyDtoList);
    }

    @PostMapping(value = "/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> addCompany(@RequestBody CompanyDto companyDto){
        CompanyDto company = companyService.addCompany(companyDto);
        if(company == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Could not find the sector with name: "+companyDto.getSectorName());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(company);
    }

    @PutMapping(value = "/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> editCompany(@RequestBody CompanyDto companyDto){
        CompanyDto company = companyService.editCompany(companyDto);
        if(company == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Could not find the company with id: "+companyDto.getId());
        }
        return ResponseEntity.ok(company);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteById(@PathVariable long id){
        try{
            String message = companyService.deleteById(id);
            return ResponseEntity.status(200).body(message);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping(value = "/{companyName}/addExchangeCodes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> mapCompanyExchangeCode(@PathVariable String companyName,
                                                         @RequestBody List<CompanyExchangeCodeMappingDto> exchangeCodes){
        try{
            String message = companyService.mapCompanyExchangeCode(companyName,exchangeCodes);
            if(message == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Could not find company with name: "+companyName);
            }
            return ResponseEntity.ok(message);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(value = "/{companyName}/ipo")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Object> getCompanyIpoDetails(@PathVariable String companyName){
        try{
            IpoDto ipoDto = companyService.getCompanyIpoDetails(companyName);
            return ResponseEntity.ok(ipoDto);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
