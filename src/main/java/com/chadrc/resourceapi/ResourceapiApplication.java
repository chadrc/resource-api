package com.chadrc.resourceapi;

import com.chadrc.resourceapi.services.ReflectionResourceService;
import com.chadrc.resourceapi.services.ResourceService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ResourceapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResourceapiApplication.class, args);
	}

	@Bean
	public ResourceService resourceService() {
		return new ReflectionResourceService();
	}
}
