package com.smc.stockmarketcharting.controllers;

import com.smc.stockmarketcharting.AbstractTest;
import com.smc.stockmarketcharting.dtos.StockPriceDto;
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
import java.util.Map;
import java.util.TreeMap;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StockPriceControllerTest extends AbstractTest {

    String baseUrl = "/stock-price";

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser
    @Order(1)
    public void findAllTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",hasSize(1)));
    }

    @Test
    @WithMockUser
    @Order(2)
    public void getCompanyStockPriceDataTest() throws Exception{
        String url = baseUrl + "/Reliance";
        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",isA(ArrayList.class)));
    }

    @Test
    @WithMockUser
    @Order(3)
    public void getStockPricesForCompanyComparisonTest() throws Exception{
        String url = baseUrl + "/company/1/BSE/2017-05-03/2017-05-05/Day";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Map<String,Double> expectedResult = new TreeMap<>();
        expectedResult.put("2017-05-04", 154.34);
        String expectedJson = super.mapToJson(expectedResult);

        String actualJson = mvcResult.getResponse().getContentAsString();

        assertEquals(expectedJson,actualJson);
    }

    @Test
    @WithMockUser
    @Order(4)
    public void getStockPricesForSectorComparisonTest() throws Exception{
        String url = baseUrl + "/sector/4/BSE/2017-05-03/2017-05-05/Day";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Map<String,Double> expectedResult = new TreeMap<>();
        expectedResult.put("2017-05-04", 154.34);
        String expectedJson = super.mapToJson(expectedResult);

        String actualJson = mvcResult.getResponse().getContentAsString();

        assertEquals(expectedJson,actualJson);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @Order(5)
    public void addStockPriceTest() throws Exception{
        String url = baseUrl + "/addList";

        StockPriceDto stockPriceDto = new StockPriceDto();
        stockPriceDto.setCompanyCode("500325");
        stockPriceDto.setStockExchangeName("BSE");
        stockPriceDto.setPrice((float)153.25);
        stockPriceDto.setDate("2017-05-05");
        stockPriceDto.setTime("10:05:00");
        List<StockPriceDto> priceDtoList = new ArrayList<>();
        priceDtoList.add(stockPriceDto);

        String json = super.mapToJson(priceDtoList);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",isA(ArrayList.class)));
    }



}
