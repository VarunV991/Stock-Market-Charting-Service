package com.smc.stockmarketcharting.controllers;

import com.smc.stockmarketcharting.AbstractTest;
import com.smc.stockmarketcharting.dtos.SectorDto;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SectorControllerTest extends AbstractTest {

    String baseUrl = "/sector";

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser
    @Order(1)
    public void findAllTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",hasSize(5)));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @Order(2)
    public void addSectorTest() throws Exception{
        String url = baseUrl + "/add";
        SectorDto sectorDto = new SectorDto("Manufacturing",
                "Companies that makes products from " +
                        "raw materials by the use of manual labor or machinery");

        String json = super.mapToJson(sectorDto);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Manufacturing")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description",Matchers.is("Companies that makes products from " +
                        "raw materials by the use of manual labor or machinery")));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @Order(3)
    public void editSectorTest() throws Exception{
        String url = baseUrl + "/edit";
        SectorDto sectorDto = new SectorDto(1,"Finance",
                "Companies that assist in Banking and Accounting");

        String json = super.mapToJson(sectorDto);

        mockMvc.perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Finance")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description",
                        Matchers.is("Companies that assist in Banking and Accounting")));
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    @Order(4)
    void deleteByIdTest() throws Exception{
        String url = baseUrl + "/2";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(url))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content,"Successfully deleted the sector");
    }

    @Test
    @WithMockUser
    @Order(5)
    void getCompaniesTest() throws Exception{
        String url = baseUrl + "/Materials/companies";
        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",isA(ArrayList.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",hasSize(1)));
    }
}
