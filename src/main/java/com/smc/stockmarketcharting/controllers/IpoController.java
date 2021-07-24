package com.smc.stockmarketcharting.controllers;

import com.smc.stockmarketcharting.dtos.IpoDto;
import com.smc.stockmarketcharting.services.IpoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/ipo")
public class IpoController {

    @Autowired
    IpoService ipoService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable long id){
        IpoDto ipoDto = ipoService.findById(id);
        if(ipoDto == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find ipo with id: "+id);
        }
        return ResponseEntity.ok(ipoDto);
    }

    @GetMapping("")
    public ResponseEntity<List<IpoDto>> findAll(){
        return ResponseEntity.ok(ipoService.findAll());
    }

    @PostMapping("/add")
    public ResponseEntity<Object> save(@RequestBody IpoDto ipoDto){
        IpoDto ipo = ipoService.save(ipoDto);
        if(ipo == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Could not find company with name: "+ipoDto.getCompanyName());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(ipo);
    }

    @PutMapping("/edit")
    public ResponseEntity<IpoDto> update(@RequestBody IpoDto ipoDto){
        IpoDto ipo = ipoService.update(ipoDto);
        return ResponseEntity.ok(ipo);
    }
}
