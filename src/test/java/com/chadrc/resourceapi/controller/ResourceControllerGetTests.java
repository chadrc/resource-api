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

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApiApplicationTests.class)
public class ResourceControllerGetTests {

    @Autowired
    private ResourceControllerProxy resourceControllerProxy;

    @Test
    public void getUserSucceeds() {
        User john = resourceControllerProxy.addUser("John", "Doe");
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().get(new GetRequest("User", john.getId()));
        Object obj = responseEntity.getBody();
        assert obj instanceof User;
        User user = (User) obj;
        assertEquals(user.getFirstName(), "John");
    }

    @Test
    public void getUnknownResourceYields400() {
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().get(new GetRequest("Animal", "594dc2f7a249e661727c6b50"));
        assert responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST;
    }

    @Test
    public void getNonExistentUserYields404() {
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().get(new GetRequest("User", "594dc2f7a249e661727c6b50"));
        assert responseEntity.getStatusCode() == HttpStatus.NOT_FOUND;
    }

    @Test
    public void getWithNullResourceNameYields400() {
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().get(new GetRequest(null, "594dc2f7a249e661727c6b50"));
        assert responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST;
    }

    @Test
    public void getWithNullIdYields400() {
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController().get(new GetRequest("User", null));
        assert responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST;
    }
}
