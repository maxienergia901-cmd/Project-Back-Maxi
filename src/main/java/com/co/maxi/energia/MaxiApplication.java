package com.co.maxi.energia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MaxiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaxiApplication.class, args);
	}

}
