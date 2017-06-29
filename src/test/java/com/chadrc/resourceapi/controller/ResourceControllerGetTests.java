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

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApiApplicationTests.class)
public class ResourceControllerGetTests {

    private ResourceController resourceController;

    @Autowired
    public void setResourceController(ResourceService resourceService) {
        this.resourceController = new ResourceController(resourceService);
    }

    @Test
    public void getUserSucceeds() {
        User john = createUser("John", "Doe");
        ResponseEntity<Object> responseEntity = resourceController.get(new GetOptions("User", john.getId()));
        Object obj = responseEntity.getBody();
        assert obj instanceof User;
        User user = (User) obj;
        assertEquals(user.getFirstName(), "John");
    }

    @Test
    public void getUnknownResourceYields400() {
        ResponseEntity<Object> responseEntity = resourceController.get(new GetOptions("Animal", "12345"));
        assert responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST;
    }

    @Test
    public void getNonExistentUserYields404() {
        ResponseEntity<Object> responseEntity = resourceController.get(new GetOptions("User", "594dc2f7a249e661727c6b50"));
        assert responseEntity.getStatusCode() == HttpStatus.NOT_FOUND;
    }

    private User createUser(String firstName, String lastName) {
        List<FieldValue> fieldValues = new ArrayList<FieldValue>(){{
            add(new FieldValue("firstName", firstName));
            add(new FieldValue("lastName", lastName));
        }};

        ResponseEntity<Object> responseEntity = resourceController.create(new CreateOptions("User", fieldValues));
        return (User) responseEntity.getBody();
    }
}
