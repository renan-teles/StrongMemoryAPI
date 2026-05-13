package com.strongmemoryapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class StrongMemoryApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(StrongMemoryApiApplication.class, args);
	}

}