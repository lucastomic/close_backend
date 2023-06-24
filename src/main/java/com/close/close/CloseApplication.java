package com.close.close;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CloseApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloseApplication.class, args);
	}

}
