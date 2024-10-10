package com.prgama.foodcourt_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FoodcourtMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodcourtMicroserviceApplication.class, args);
	}

}
