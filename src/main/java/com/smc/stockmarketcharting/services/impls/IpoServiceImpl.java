package com.smc.stockmarketcharting.services.impls;

import com.smc.stockmarketcharting.dtos.IpoDto;
import com.smc.stockmarketcharting.mappers.IpoMapper;
import com.smc.stockmarketcharting.models.Company;
import com.smc.stockmarketcharting.models.Ipo;
import com.smc.stockmarketcharting.repositories.CompanyRepository;
import com.smc.stockmarketcharting.repositories.IpoRepository;
import com.smc.stockmarketcharting.services.IpoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IpoServiceImpl implements IpoService {

    @Autowired
    IpoRepository ipoRepository;

    @Autowired
    IpoMapper ipoMapper;

    @Autowired
    CompanyRepository companyRepository;

    @Override
    public List<IpoDto> findAll() {
        return new ArrayList<>(ipoMapper.toIpoDtos(ipoRepository.findAll()));
    }

    @Override
    public IpoDto findById(long id) {
        return ipoRepository.findById(id).map(ipo ->
                ipoMapper.toIpoDto(ipo)).orElse(null);
    }

    @Override
    public IpoDto save(IpoDto ipoDto) {
        Ipo ipo = ipoMapper.toIpo(ipoDto);
        if(ipoDto.getCompanyName()!=null){
            Company company = companyRepository.findCompanyByName(ipoDto.getCompanyName()).orElse(null);
            if(company!=null){
                ipo.setCompany(company);
                ipo = ipoRepository.save(ipo);
                return ipoMapper.toIpoDto(ipo);
            }
        }
        return null;
    }

    @Override
    public IpoDto update(IpoDto ipoDto) {
        Optional<Ipo> ipoOptional = ipoRepository.findById(ipoDto.getId());
        if(ipoOptional.isPresent()){
            Ipo ipo = ipoMapper.toIpo(ipoDto);
            if(ipoDto.getCompanyName()!=null){
                Company company = companyRepository.findCompanyByName(ipoDto.getCompanyName())
                        .orElse(null);
                if(company!=null){
                    ipo.setCompany(company);
                    company.setIpo(ipo);
                    companyRepository.save(company);
                }
                else{
                    ipo.setCompany(null);
                }
            }
            else{
                ipo.setCompany(null);
            }
            ipo = ipoRepository.save(ipo);
        }
        return null;
    }

    @Override
    public String deleteById(long id) {
        try{
            ipoRepository.deleteById(id);
            return "Successfully deleted the ipo";
        }
        catch (Exception e) {
            throw new RuntimeException("Could delete the ipo: " + e.getMessage());
        }
    }
}
