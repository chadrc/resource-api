package com.chadrc.resourceapi.controller;

import com.chadrc.resourceapi.ResourceApiApplicationTests;
import com.chadrc.resourceapi.service.OptionsResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApiApplicationTests.class)
public class ResourceControllerOptionsTests {

    @Autowired
    private ResourceControllerProxy resourceControllerProxy;

    @Test
    public void optionsSucceeds() {
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().options();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void optionsReturnsOptionsResult() {
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().options();
        assert responseEntity.getBody() instanceof OptionsResult;
    }

    @Test
    public void optionsHasUserEntry() {
        OptionsResult optionsResult = getOptions();
        assert optionsResult.getOptions().containsKey("User");
    }

    private OptionsResult getOptions() {
        return (OptionsResult) resourceControllerProxy.getResourceController().options().getBody();
    }
}
