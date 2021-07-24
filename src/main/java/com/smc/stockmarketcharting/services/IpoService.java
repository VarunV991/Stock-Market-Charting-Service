package com.smc.stockmarketcharting.services;

import com.smc.stockmarketcharting.dtos.IpoDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IpoService {
    public List<IpoDto> findAll();
    public IpoDto findById(long id);
    public IpoDto save(IpoDto ipoDto);
    public IpoDto update(IpoDto ipoDto);
}
