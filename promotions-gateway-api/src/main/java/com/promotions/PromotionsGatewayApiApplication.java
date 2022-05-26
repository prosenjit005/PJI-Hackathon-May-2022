package com.promotions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PromotionsGatewayApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PromotionsGatewayApiApplication.class, args);
	}

}
