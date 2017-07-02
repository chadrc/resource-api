package com.chadrc.resourceapi.controller;

import com.chadrc.resourceapi.ResourceApiApplicationTests;
import com.chadrc.resourceapi.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApiApplicationTests.class)
public class ResourceControllerCreateTests {

    @Autowired
    private ResourceControllerProxy resourceControllerProxy;

    @Test
    public void createDefaultSucceeds() {
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().create(new CreateRequest("User", new ArrayList<>()));
        Object obj = responseEntity.getBody();
        assert(obj instanceof User);
    }

    @Test
    public void createDefaultWithNullArgumentsSucceeds() {
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().create(new CreateRequest("User", null));
        Object obj = responseEntity.getBody();
        assert(obj instanceof User);
    }

    @Test
    public void createWithParameterSucceeds() {
        List<FieldValue> fieldValues = new ArrayList<FieldValue>(){{
                add(new FieldValue("firstName", "John"));
                add(new FieldValue("lastName", "Doe"));
        }};

        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().create(new CreateRequest("User", fieldValues));
        User user = (User) responseEntity.getBody();
        assertEquals(user.getFirstName(), "John");
        assertEquals(user.getLastName(), "Doe");
    }

    @Test
    public void errorDuringConstructorCallYields400() {
        List<FieldValue> fieldValues = new ArrayList<FieldValue>(){{
            add(new FieldValue("firstName", "Invalid"));
            add(new FieldValue("lastName", "Name"));
        }};

        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().create(new CreateRequest("User", fieldValues));
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void createWithIncorrectFieldValueYields400() {
        List<FieldValue> fieldValues = new ArrayList<FieldValue>(){{
            add(new FieldValue("firstName", "John"));
            add(new FieldValue("lastName", 1000));
        }};

        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().create(new CreateRequest("User", fieldValues));
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createWithUnknownResourceYields400() {
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().create(new CreateRequest("Animal", new ArrayList<>()));
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createWithNullResourceNameYields400() {
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().create(new CreateRequest(null, new ArrayList<>()));
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
}
