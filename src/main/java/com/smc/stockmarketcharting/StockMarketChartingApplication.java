package com.smc.stockmarketcharting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class StockMarketChartingApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockMarketChartingApplication.class, args);
	}

}
