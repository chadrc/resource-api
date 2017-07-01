package com.chadrc.resourceapi.controller;

import com.chadrc.resourceapi.ResourceApiApplicationTests;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApiApplicationTests.class)
public class ResourceControllerActionTests {

    @Autowired
    private ResourceControllerProxy resourceControllerProxy;

    @Test
    public void actionWithUnknownResourceYields400() {
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController()
                .action(new ActionOptions(
                        "fakeAction",
                        "Animal",
                        "594dc2f7a249e661727c6b50",
                        new ArrayList<>()
                ));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void actionWithUnknownActionYields400() {
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController()
                .action(new ActionOptions(
                        "fakeAction",
                        "User",
                        "594dc2f7a249e661727c6b50",
                        new ArrayList<>()
                ));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}
