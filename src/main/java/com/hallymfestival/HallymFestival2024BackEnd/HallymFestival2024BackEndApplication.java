package com.hallymfestival.HallymFestival2024BackEnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class HallymFestival2024BackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(HallymFestival2024BackEndApplication.class, args);
	}

}
