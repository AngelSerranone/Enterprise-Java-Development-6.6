package com.ironhack.bookFormatService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BookFormatServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookFormatServiceApplication.class, args);
	}

}
