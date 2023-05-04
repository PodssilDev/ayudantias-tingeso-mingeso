package com.tingeso.autorizacionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AutorizacionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutorizacionServiceApplication.class, args);
	}

}
