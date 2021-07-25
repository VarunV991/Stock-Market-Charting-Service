package com.smc.stockmarketcharting.controllers;

import com.smc.stockmarketcharting.AbstractTest;
import com.smc.stockmarketcharting.dtos.IpoDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;


@AutoConfigureMockMvc
@SpringBootTest
public class IpoControllerTest extends AbstractTest {

    @Autowired
    MockMvc mockMvc;

    String baseUrl = "/ipo";

    @Test
    @WithMockUser
    void allIPOTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ipo"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",hasSize(1)));
    }

    @Test
    @WithMockUser
    void findIpoByIdTest() throws Exception {
        String url = baseUrl + "/1";
        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.pricePerShare",Matchers.is(121.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalShares",Matchers.is(7895)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.openDateTime",Matchers.is("2017-06-13T11:08:04.017494")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.remarks",Matchers.is("Good")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.exchangeName",Matchers.is("BSE")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName",Matchers.is("Reliance")));
    }

    @Test
    @WithMockUser
    void addIPOTest() throws Exception {
        String url = baseUrl + "/add";
        IpoDto ipoDto = new IpoDto();
        ipoDto.setId(2);
        ipoDto.setExchangeName("BSE");
        ipoDto.setRemarks("Good");
        ipoDto.setPricePerShare(124);
        ipoDto.setOpenDateTime("2021-07-16T11:08:04.017494");
        ipoDto.setTotalShares(212);
        ipoDto.setCompanyName("TCS");

        String json = super.mapToJson(ipoDto);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.pricePerShare",Matchers.is(124.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalShares",Matchers.is(212)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.openDateTime",Matchers.is("2021-07-16T11:08:04.017494")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.remarks",Matchers.is("Good")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.exchangeName",Matchers.is("BSE")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName",Matchers.is("TCS")));
    }

    @Test
    @WithMockUser
    void editIpoTest() throws Exception{
        String url = baseUrl + "/edit";

        IpoDto ipoDto = new IpoDto();
        ipoDto.setId(1);
        ipoDto.setExchangeName("NSE");
        ipoDto.setRemarks("Good Ipo");
        ipoDto.setPricePerShare(135.8);
        ipoDto.setOpenDateTime("2018-04-05T10:28:32.018594");
        ipoDto.setTotalShares(7800);
        ipoDto.setCompanyName("Reliance");

        String json = super.mapToJson(ipoDto);

        mockMvc.perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.pricePerShare",Matchers.is(135.8)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalShares",Matchers.is(7800)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.openDateTime",Matchers.is("2018-04-05T10:28:32.018594")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.remarks",Matchers.is("Good Ipo")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.exchangeName",Matchers.is("NSE")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName",Matchers.is("Reliance")));
    }
}
