package com.smc.stockmarketcharting;

import com.smc.stockmarketcharting.models.ERole;
import com.smc.stockmarketcharting.models.Role;
import com.smc.stockmarketcharting.models.User;
import com.smc.stockmarketcharting.repositories.RoleRepository;
import com.smc.stockmarketcharting.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@EnableEurekaClient
@SpringBootApplication
public class StockMarketChartingApplication {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;


	public static void main(String[] args) {
		SpringApplication.run(StockMarketChartingApplication.class, args);
	}

}
