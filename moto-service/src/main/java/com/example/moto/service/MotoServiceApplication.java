package com.example.moto.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication

//Anotación de Eureka Client
@EnableEurekaClient

public class MotoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MotoServiceApplication.class, args);
	}

}
