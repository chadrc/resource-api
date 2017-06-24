package com.chadrc.resourceapiexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.chadrc.resourceapi"})
public class ResourceApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(ResourceApiApplication.class, args);
	}
}
