package com.chadrc.resourceapi;

import com.chadrc.resourceapi.controller.ResourceController;
import com.chadrc.resourceapi.service.ReflectionResourceService;
import com.chadrc.resourceapi.service.ResourceModel;
import com.chadrc.resourceapi.service.ResourceService;
import com.chadrc.resourceapi.store.InMemoryResourceStore;
import com.chadrc.resourceapi.store.ResourceRepository;
import com.chadrc.resourceapi.store.ResourceStore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResourceapiApplicationTests {

	@Configuration
	@ComponentScan(basePackages = "com.chadrc.resourceapi.tests")
	static class ContextConfiguration {
		@Bean
		@Primary
		public ResourceStore resourceStore () {
			return new InMemoryResourceStore();
		}

		@Bean
		@Autowired
		public ResourceService resourceService(ResourceStore resourceStore, List<ResourceModel> resourceRepositories) {
			return new ReflectionResourceService(resourceStore, resourceRepositories);
		}
	}

	private ResourceController resourceController;

	@Autowired
	public void setResourceService(ResourceService resourceService) {
		resourceController = new ResourceController(resourceService);
	}

	@Test
	public void contextLoads() {
	}

}
