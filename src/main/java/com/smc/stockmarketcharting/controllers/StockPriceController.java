package com.smc.stockmarketcharting.controllers;

import com.smc.stockmarketcharting.dtos.StockPriceDto;
import com.smc.stockmarketcharting.dtos.StockPriceOutputDto;
import com.smc.stockmarketcharting.repositories.StockPriceRepository;
import com.smc.stockmarketcharting.services.StockPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/stock-price")
public class StockPriceController {
    @Autowired
    StockPriceRepository stockPriceRepository ;

    @Autowired
    StockPriceService stockPriceService;

    @PostMapping(value = "/add",consumes = "application/json")
    public ResponseEntity<Object> save(@RequestBody StockPriceDto stockPriceDto){

        StockPriceDto stockPrice = stockPriceService.save(stockPriceDto);
        if(stockPrice == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Could not save the stock price data for company code"+stockPriceDto.getCompanyCode());
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(stockPrice.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping(value = "/addList")
    public ResponseEntity<Object> save(@RequestBody List<StockPriceDto> stockPriceDtos){

        List<StockPriceDto> stockPrices = stockPriceService.saveList(stockPriceDtos);
        if(stockPrices.size() < stockPriceDtos.size()){
            return  ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                    "Some stock price data has invalid attributes.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(stockPrices);
    }

    @GetMapping(value = "",produces = "application/json")
    public ResponseEntity<List<StockPriceDto>> findAll(){
        List<StockPriceDto> stockPriceDtos = stockPriceService.findAll();
        return ResponseEntity.ok(stockPriceDtos);
    }

    @GetMapping(value = "/{name}")
    public ResponseEntity<Object> getStockPricesForCompany(@PathVariable String name){
        List<StockPriceDto> stockPriceDtos = stockPriceService.getStockPricesForCompany(name);
        if(stockPriceDtos==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "Could not find company with name: "+name
            );
        }
        return ResponseEntity.ok(stockPriceDtos);
    }

    @GetMapping("/company/{id}/{exchangeName}/{fromDate}/{toDate}/{periodicity}")
    public ResponseEntity<Object> getStockPricesForCompanyComparison(
            @PathVariable long id,@PathVariable String exchangeName,@PathVariable String fromDate,
            @PathVariable String toDate, @PathVariable String periodicity){
        try{
            Map<String,Double> stockPriceDtos =
                    stockPriceService.getStockPricesForCompanyComparison(
                            id, exchangeName, fromDate, toDate, periodicity);
            if(stockPriceDtos == null){
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Could not find company with id: "+id);
            }
            return ResponseEntity.ok(stockPriceDtos);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/sector/{id}/{exchangeName}/{fromDate}/{toDate}/{periodicity}")
    public ResponseEntity<Object> getStockPricesForSectorComparison(
            @PathVariable long id,@PathVariable String exchangeName,@PathVariable String fromDate,
            @PathVariable String toDate,@PathVariable String periodicity){
        try{
            Map<String,Double> stockPriceDtos =
                    stockPriceService.getStockPricesForSectorComparison(
                            id, exchangeName, fromDate, toDate, periodicity);
            if(stockPriceDtos == null){
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Could not find sector with id: "+id);
            }
            return ResponseEntity.ok(stockPriceDtos);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
