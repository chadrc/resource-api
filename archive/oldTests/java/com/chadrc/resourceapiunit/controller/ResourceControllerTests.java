package com.chadrc.resourceapiunit.controller;

import com.chadrc.resourceapi.controller.ResourceController;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;

public class ResourceControllerTests {

    @Test
    public void unknownErrorYields500() {
        ResourceController resourceController = new ResourceController(new FailingResourceService());
        ResponseEntity<Object> responseEntity = resourceController.options();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}
