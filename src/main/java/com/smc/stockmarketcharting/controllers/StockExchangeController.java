package com.smc.stockmarketcharting.controllers;

import com.smc.stockmarketcharting.dtos.CompanyDto;
import com.smc.stockmarketcharting.dtos.StockExchangeDto;
import com.smc.stockmarketcharting.services.StockExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<StockExchangeDto>> findAll(){
        return ResponseEntity.ok(stockExchangeService.findAll());
    }

    @PostMapping("/add")
    public ResponseEntity<StockExchangeDto> save(@RequestBody StockExchangeDto stockExchangeDto){
        StockExchangeDto stockExchange = stockExchangeService.save(stockExchangeDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(stockExchange.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/edit")
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
