package com.smc.stockmarketcharting.controllers;

import com.smc.stockmarketcharting.dtos.CompanyDto;
import com.smc.stockmarketcharting.dtos.StockExchangeDto;
import com.smc.stockmarketcharting.services.StockExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/stock-exchange")
public class StockExchangeController{

    @Autowired
    StockExchangeService stockExchangeService;

    @GetMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<StockExchangeDto>> findAll(){
        return ResponseEntity.ok(stockExchangeService.findAll());
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StockExchangeDto> save(@RequestBody StockExchangeDto stockExchangeDto){
        StockExchangeDto stockExchange = stockExchangeService.save(stockExchangeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(stockExchange);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> update(@RequestBody StockExchangeDto stockExchangeDto){
        StockExchangeDto stockExchange = stockExchangeService.update(stockExchangeDto);
        if(stockExchange==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "Could not find exchange with id: "+stockExchangeDto.getId()
            );
        }
        return ResponseEntity.ok(stockExchange);
    }

    @GetMapping("/{name}/companies")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Object> getAllCompanies(@PathVariable String name){
        List<CompanyDto> companyDtos = stockExchangeService.getAllCompanies(name);
        if(companyDtos==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "Could not find stock exchange with name: "+name
            );
        }
        return ResponseEntity.ok(companyDtos);
    }
}
