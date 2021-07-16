package com.smc.stockmarketcharting.controllers;

import com.smc.stockmarketcharting.AbstractTest;
import com.smc.stockmarketcharting.dtos.CompanyDto;
import com.smc.stockmarketcharting.dtos.CompanyExchangeCodeMappingDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CompanyController.class)
public class CompanyControllerTest extends AbstractTest {

    final String baseUri = "/company";

    @Test
    public void findByIdTest() throws Exception{
        String uri = baseUri + "/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);
    }

    @Test
    public void findAllTest() throws Exception{
        String uri = baseUri;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);
        String content = mvcResult.getResponse().getContentAsString();
        CompanyDto[] companyDtos = super.mapFromJson(content,CompanyDto[].class);
        assertTrue(companyDtos.length>0);
    }

    @Test
    public void addCompanyTest() throws Exception{
        String uri = baseUri + "/add";
        CompanyDto company = new CompanyDto();
        company.setName("Reliance");
        company.setCeo("Mukesh");
        company.setDescription("Largest company in India");
        company.setTurnover("$3 Billion/year");
        company.setBoardOfDirectors("Mukesh,Nita,Ria,Akash");
        company.setSectorName("Consumer Products");

        String input = super.mapToJson(company);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(input)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    public void editCompanyTest() throws Exception{
        String uri = baseUri + "/edit";
        CompanyDto company = new CompanyDto();
        company.setId(1);
        company.setName("Reliance");
        company.setCeo("Mukesh");
        company.setDescription("Largest company in Asia");
        company.setTurnover("$3 Billion/year");
        company.setBoardOfDirectors("Mukesh,Nita,Ria,Akash");
        company.setSectorName("Consumer Products");

        String input = super.mapToJson(company);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(input)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void deleteByIdTest() throws Exception{
        String uri = baseUri + "/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "Successfully deleted the company");
    }

    @Test
    public void mapCompanyExchangeCodeTest() throws Exception{
        String uri = baseUri + "/Reliance/addExchangeCodes";
        List<CompanyExchangeCodeMappingDto> codeMappingDtos = new ArrayList<>();
        CompanyExchangeCodeMappingDto codeMapping = new CompanyExchangeCodeMappingDto();
        codeMapping.setExchangeName("BSE");
        codeMapping.setCompanyCode("500325");
        codeMappingDtos.add(codeMapping);

        String input = super.mapToJson(codeMappingDtos);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(input)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }
}
