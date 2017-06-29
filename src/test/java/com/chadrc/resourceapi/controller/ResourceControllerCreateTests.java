package com.chadrc.resourceapi.controller;

import com.chadrc.resourceapi.ResourceApiApplicationTests;
import com.chadrc.resourceapi.domain.User;
import com.chadrc.resourceapi.service.ResourceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApiApplicationTests.class)
public class ResourceControllerCreateTests {

    private ResourceController resourceController;

    @Autowired
    public void setResourceController(ResourceService resourceService) {
        this.resourceController = new ResourceController(resourceService);
    }

    @Test
    public void createSucceeds() {
        ResponseEntity<Object> responseEntity = resourceController.create(new CreateOptions("User", new ArrayList<>()));
        Object obj = responseEntity.getBody();
        assert(obj instanceof User);
    }

    @Test
    public void createWithParameterSucceeds() {
        List<FieldValue> fieldValues = new ArrayList<FieldValue>(){{
                add(new FieldValue("firstName", "John"));
                add(new FieldValue("lastName", "Doe"));
        }};

        ResponseEntity<Object> responseEntity = resourceController.create(new CreateOptions("User", fieldValues));
        User user = (User) responseEntity.getBody();
        assertEquals(user.getFirstName(), "John");
        assertEquals(user.getLastName(), "Doe");
    }

    @Test
    public void createWithIncorrectFieldValueYields400() {
        List<FieldValue> fieldValues = new ArrayList<FieldValue>(){{
            add(new FieldValue("firstName", "John"));
            add(new FieldValue("lastName", 1000));
        }};

        ResponseEntity<Object> responseEntity = resourceController.create(new CreateOptions("User", fieldValues));
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
}
