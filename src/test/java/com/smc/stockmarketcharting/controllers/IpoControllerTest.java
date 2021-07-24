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


@AutoConfigureMockMvc
@SpringBootTest
public class IpoControllerTest extends AbstractTest {

    @Autowired
    MockMvc mockMvc;

    String baseUrl = "/ipo";

    @Test
    @WithMockUser
    void newIPOTest() throws Exception {
        String url = baseUrl + "/add";
        IpoDto ipoDto = new IpoDto();
        ipoDto.setId(2);
        ipoDto.setExchangeName("BSE");
        ipoDto.setRemarks("Good");
        ipoDto.setPricePerShare(124);
        ipoDto.setOpenDateTime("2021-07-16T11:08:04.017494");
        ipoDto.setTotalShares(212);
        ipoDto.setCompanyName("Reliance");

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
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName",Matchers.is("Reliance")));
    }
}
