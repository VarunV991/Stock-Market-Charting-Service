package com.smc.stockmarketcharting.controllers;

import com.smc.stockmarketcharting.dtos.CompanyDto;
import com.smc.stockmarketcharting.dtos.CompanyExchangeCodeMappingDto;
import com.smc.stockmarketcharting.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findById(@PathVariable long id){
        CompanyDto companyDto = companyService.findById(id);
        if(companyDto == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Could not find the company with id: "+id);
        }
        return ResponseEntity.ok(companyDto);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<CompanyDto>> findAll(){
        List<CompanyDto> companyDtoList = companyService.findAll();
        return ResponseEntity.ok(companyDtoList);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Object> addCompany(@RequestBody CompanyDto companyDto){
        CompanyDto company = companyService.addCompany(companyDto);
        if(company == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not add the company");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(company);
    }

    @PutMapping(value = "/edit")
    public ResponseEntity<Object> editCompany(@RequestBody CompanyDto companyDto){
        CompanyDto company = companyService.editCompany(companyDto);
        if(company == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Could not find the company with id: "+companyDto.getId());
        }
        return ResponseEntity.ok(company);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id){
        try{
            String message = companyService.deleteById(id);
            return ResponseEntity.ok(message);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping(value = "/{companyName}/addExchangeCodes")
    public ResponseEntity<Object> mapCompanyExchangeCode(@PathVariable String companyName,
                                                         @RequestBody List<CompanyExchangeCodeMappingDto> exchangeCodes){
        CompanyDto companyDto = companyService.mapCompanyExchangeCode(companyName,exchangeCodes);
        if(companyDto == null){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body("Could not map exchange codes for the company with name: "+companyName);
        }
        return ResponseEntity.ok(companyDto);
    }
}
