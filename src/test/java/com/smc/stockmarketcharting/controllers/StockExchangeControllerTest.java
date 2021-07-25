package com.smc.stockmarketcharting.controllers;

import com.smc.stockmarketcharting.AbstractTest;
import com.smc.stockmarketcharting.dtos.StockExchangeDto;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;

@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StockExchangeControllerTest extends AbstractTest {

    String baseUrl = "/stock-exchange";

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser
    @Order(1)
    public void findAllTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",hasSize(2)));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @Order(2)
    public void addExchangeTest() throws Exception{
        String url = baseUrl + "/add";
        StockExchangeDto stockExchangeDto = new StockExchangeDto();
        stockExchangeDto.setName("SSE");
        stockExchangeDto.setDescription("Shanghai Stock Exchange");
        stockExchangeDto.setAddress("Shanghai,China");
        stockExchangeDto.setRemarks("One of the two stock exchanges operating independently in mainland China");

        String json = super.mapToJson(stockExchangeDto);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("SSE")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description",Matchers.is("Shanghai Stock Exchange")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address",Matchers.is("Shanghai,China")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.remarks",Matchers.is("One of the two stock exchanges " +
                        "operating independently in mainland China")));

    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @Order(3)
    public void editExchangeTest() throws Exception{
        String url = baseUrl + "/edit";
        StockExchangeDto stockExchangeDto = new StockExchangeDto();
        stockExchangeDto.setId(1);
        stockExchangeDto.setName("BSE");
        stockExchangeDto.setDescription("Bombay Stock Exchange");
        stockExchangeDto.setAddress("Dalal Street, Mumbai, India");
        stockExchangeDto.setRemarks("World's 11th largest stock-exchange");

        String json = super.mapToJson(stockExchangeDto);

        mockMvc.perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("BSE")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description",
                        Matchers.is("Bombay Stock Exchange")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address",
                        Matchers.is("Dalal Street, Mumbai, India")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.remarks",
                        Matchers.is("World's 11th largest stock-exchange")));

    }

    @Test
    @WithMockUser
    @Order(4)
    public void getCompaniesTest() throws Exception{
        String url = baseUrl + "/BSE/companies";
        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",isA(ArrayList.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",hasSize(1)));
    }
}
