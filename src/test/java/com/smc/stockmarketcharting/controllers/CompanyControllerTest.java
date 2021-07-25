package com.smc.stockmarketcharting.controllers;

import com.smc.stockmarketcharting.AbstractTest;

import com.smc.stockmarketcharting.dtos.CompanyDto;
import com.smc.stockmarketcharting.dtos.CompanyExchangeCodeMappingDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CompanyControllerTest extends AbstractTest {

    @Autowired
    MockMvc mockMvc;

    String baseUrl = "/company";

    @Test
    @WithMockUser
    @Order(1)
    void findByIdTest() throws Exception {
        String url = baseUrl + "/1";
        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",Matchers.is("Reliance")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.turnover",Matchers.is("$3.1 Billion/year")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ceo",Matchers.is("Mukesh")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description",Matchers.is("Largest Conglomerate")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.boardOfDirectors",Matchers.is("Mukesh,Nita,Anil")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sectorName",Matchers.is("Materials")));
    }

    @Test
    @WithMockUser
    @Order(2)
    void findAllTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",hasSize(2)));
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    @Order(3)
    void addCompanyTest() throws Exception {
        String url = baseUrl + "/add";
        CompanyDto companyDto = new CompanyDto();
        companyDto.setName("Axis");
        companyDto.setTurnover("$1.5 Billion/year");
        companyDto.setCeo("Amitabh");
        companyDto.setBoardOfDirectors("Amitabh,Shikha,Aditya");
        companyDto.setDescription("Third largest private sector bank in India");
        companyDto.setSectorName("Finance");

        String json = super.mapToJson(companyDto);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",Matchers.is("Axis")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.turnover",Matchers.is("$1.5 Billion/year")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ceo",Matchers.is("Amitabh")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description",Matchers.is("Third largest private sector bank in India")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.boardOfDirectors",Matchers.is("Amitabh,Shikha,Aditya")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sectorName",Matchers.is("Finance")));
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    @Order(4)
    void editCompanyTest() throws Exception{
        String url = baseUrl + "/edit";
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(1);
        companyDto.setName("Reliance");
        companyDto.setTurnover("$2.8 Billion/year");
        companyDto.setCeo("Mukesh");
        companyDto.setBoardOfDirectors("Akash,Nita,Anil");
        companyDto.setDescription("Indian multinational conglomerate company");
        companyDto.setSectorName("Materials");

        String json = super.mapToJson(companyDto);

        mockMvc.perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",Matchers.is("Reliance")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.turnover",Matchers.is("$2.8 Billion/year")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ceo",Matchers.is("Mukesh")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description",Matchers.is("Indian multinational conglomerate company")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.boardOfDirectors",Matchers.is("Akash,Nita,Anil")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sectorName",Matchers.is("Materials")));
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    @Order(5)
    void deleteByIdTest() throws Exception{
        String url = baseUrl + "/2";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(url))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content,"Successfully deleted the company");
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @Order(6)
    void mapCompanyExchangeCodeTest() throws Exception{
        String url = baseUrl + "/Reliance/addExchangeCodes";

        List<CompanyExchangeCodeMappingDto> exchangeCodes = new ArrayList<>();
        exchangeCodes.add(new CompanyExchangeCodeMappingDto("BSE","500325"));

        String json = super.mapToJson(exchangeCodes);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content,"Successfully mapped all the Company Exchange Codes");
    }

    @Test
    @WithMockUser
    @Order(7)
    void getIpoDetailsTest() throws Exception{
        String url = baseUrl + "/Reliance/ipo";

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.pricePerShare",Matchers.is(121.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalShares",Matchers.is(7895)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.openDateTime",Matchers.is("2017-06-13T11:08:04.017494")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.remarks",Matchers.is("Good")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.exchangeName",Matchers.is("BSE")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName",Matchers.is("Reliance")));
    }

}
