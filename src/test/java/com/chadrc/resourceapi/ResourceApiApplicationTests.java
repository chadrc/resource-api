package com.chadrc.resourceapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApiApplicationTests.class)
@ComponentScan(basePackages = "com.chadrc.resourceapi")
@AutoConfigureDataMongo
public class ResourceApiApplicationTests {

	@Test
	public void contextLoads() {
	}

}
