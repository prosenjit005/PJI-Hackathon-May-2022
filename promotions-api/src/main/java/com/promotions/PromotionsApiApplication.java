package com.promotions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableWebSecurity
@EnableEurekaClient
@OpenAPIDefinition(info = @Info(title = "Promotions API", version = "1.0", description = "Promotions api"))
public class PromotionsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PromotionsApiApplication.class, args);
	}

}
