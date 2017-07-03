package com.chadrc.resourceapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreTests.class)
@ComponentScan(basePackages = "com.chadrc.resourceapi")
public class CoreTests {

    @Autowired
    private ResourceController resourceController;

    @Bean
    public ResourceService getResourceService() {
        return new MockResourceService();
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void callToGetMockServiceSucceeds() {
        GetRequest request = new GetRequest("SuccessValue");
        ResponseEntity<GetResponse> responseEntity = resourceController.get(request);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}