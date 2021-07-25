package com.smc.stockmarketcharting.controllers;

import com.smc.stockmarketcharting.AbstractTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StockPriceControllerTest extends AbstractTest {

    @Autowired
    MockMvc mockMvc;

    String baseUrl = "/stock-price";
}
