package com.smc.stockmarketcharting.mappers;

import com.smc.stockmarketcharting.dtos.IpoDto;
import com.smc.stockmarketcharting.dtos.UserDto;
import com.smc.stockmarketcharting.models.Ipo;
import com.smc.stockmarketcharting.models.User;
import com.smc.stockmarketcharting.repositories.CompanyRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class IpoMapper {

    @Autowired
    CompanyRepository companyRepository;

    public IpoDto toIpoDto(Ipo ipo){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        IpoDto ipoDto = mapper.map(ipo,IpoDto.class);
        if(ipo.getCompany() != null){
            ipoDto.setCompanyName(ipo.getCompany().getName());
        }
        return ipoDto;
    }

    public Ipo toIpo(IpoDto ipoDto){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Ipo ipo = mapper.map(ipoDto,Ipo.class);
        return ipo;
    }

    public List<IpoDto> toIpoDtos(List<Ipo> ipos){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<IpoDto> ipoDtos = new ArrayList<>();
        for (Ipo ipoDetails:ipos){
            ipoDtos.add(toIpoDto(ipoDetails));
        }
        return ipoDtos;
    }
}
