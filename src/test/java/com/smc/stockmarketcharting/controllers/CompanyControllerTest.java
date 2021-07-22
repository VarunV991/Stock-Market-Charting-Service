package com.smc.stockmarketcharting.controllers;


import com.smc.stockmarketcharting.AbstractTest;
import com.smc.stockmarketcharting.dtos.CompanyDto;
import com.smc.stockmarketcharting.models.Company;
import com.smc.stockmarketcharting.models.Sector;
import com.smc.stockmarketcharting.repositories.CompanyRepository;
import com.smc.stockmarketcharting.repositories.SectorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CompanyControllerTest extends AbstractTest {

    final String baseUri = "/company";
    Sector sector;

    @Mock
    SectorRepository sectorRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Before
    public void intializeRequiredEntites(){
        super.setUp();
        sectorRepository.deleteAll();
        this.sector = new Sector("Materials",
                "Companies that provide various goods for use in manufacturing and other applications");
        this.sector = sectorRepository.save(sector);
    }

    @Test
    public void addCompanyTest() throws Exception{
        String uri = baseUri + "/add";
        CompanyDto companyDto = new CompanyDto();
        companyDto.setName("Reliance");
        companyDto.setTurnover("$3 Billion/year");
        companyDto.setBoardOfDirectors("Mukesh,Nita,Akash");
        companyDto.setCeo("Mukesh");
        companyDto.setDescription("Largest conglomerate in India");
        companyDto.setSectorName("Materials");

        String input = super.mapToJson(companyDto);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(input)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    public void findAllTest() throws Exception{
        String uri = baseUri;

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void findByIdTest() throws Exception{
        String uri = baseUri+"/1";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        CompanyDto companyDto = super.mapFromJson(content,CompanyDto.class);
        assertTrue(companyDto.getName().equals("Tata"));
    }

}
