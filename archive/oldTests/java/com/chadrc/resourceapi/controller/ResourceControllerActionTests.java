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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApiApplicationTests.class)
public class ResourceControllerActionTests {

    @Autowired
    private ResourceControllerProxy resourceControllerProxy;

    @Test
    public void actionWithNullResourceNameYields400() {
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController()
                .action(new ActionRequest(
                        "fakeAction",
                        null,
                        "594dc2f7a249e661727c6b50",
                        new ArrayList<>()
                ));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void actionWithNullActionNameYields400() {
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController()
                .action(new ActionRequest(
                        null,
                        "Animal",
                        "594dc2f7a249e661727c6b50",
                        new ArrayList<>()
                ));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void actionWithUnknownResourceYields400() {
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController()
                .action(new ActionRequest(
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
                .action(new ActionRequest(
                        "fakeAction",
                        "User",
                        "594dc2f7a249e661727c6b50",
                        new ArrayList<>()
                ));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void actionWithTargetNoArgsSucceeds() {
        User user = resourceControllerProxy.addUser("Charles", "Xavier");
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController()
                .action(new ActionRequest(
                        "signUpForNewsletter",
                        "User",
                        user.getId(),
                        new ArrayList<>()
                ));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void actionWithTargetAndArgsSucceeds() {
        User user = resourceControllerProxy.addUser("Charles", "Xavier");
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController()
                .action(new ActionRequest(
                        "changePassword",
                        "User",
                        user.getId(),
                        new ArrayList<FieldValue>(){{
                            add(new FieldValue("currentPassword", "password"));
                            add(new FieldValue("newPassword", "newPassword"));
                        }}
                ));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void invokeActionWithoutAnnotationYields400() {
        User user = resourceControllerProxy.addUser("Charles", "Xavier");
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController()
                .action(new ActionRequest(
                        "setFirstName",
                        "User",
                        user.getId(),
                        new ArrayList<FieldValue>(){{
                            add(new FieldValue("firstName", "Charley"));
                        }}
                ));

        assertNotEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void actionWithIncorrectArgCountYields400() {
        User user = resourceControllerProxy.addUser("Charles", "Xavier");
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController()
                .action(new ActionRequest(
                        "changePassword",
                        "User",
                        user.getId(),
                        new ArrayList<FieldValue>(){{
                            add(new FieldValue("currentPassword", "password"));
                        }}
                ));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void actionWithIncorrectArgValuesYields400() {
        User user = resourceControllerProxy.addUser("Charles", "Xavier");
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController()
                .action(new ActionRequest(
                        "changePassword",
                        "User",
                        user.getId(),
                        new ArrayList<FieldValue>(){{
                            add(new FieldValue("currentPassword", 200));
                            add(new FieldValue("newPassword", 300));
                        }}
                ));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void errorDuringActionYields400() {
        User user = resourceControllerProxy.addUser("Charles", "Xavier");
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController()
                .action(new ActionRequest(
                        "changePassword",
                        "User",
                        user.getId(),
                        new ArrayList<FieldValue>(){{
                            add(new FieldValue("currentPassword", "notPassword"));
                            add(new FieldValue("newPassword", "newPassword"));
                        }}
                ));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void actionWithIncorrectResourceId() {
        User user = resourceControllerProxy.addUser("Charles", "Xavier");
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController()
                .action(new ActionRequest(
                        "changePassword",
                        "User",
                        "594dc2f7a249e661727c6b50",
                        new ArrayList<FieldValue>(){{
                            add(new FieldValue("currentPassword", "notPassword"));
                            add(new FieldValue("newPassword", "newPassword"));
                        }}
                ));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void actionWithNullResourceId() {
        User user = resourceControllerProxy.addUser("Charles", "Xavier");
        ResponseEntity<Object> responseEntity = resourceControllerProxy.getResourceController()
                .action(new ActionRequest(
                        "changePassword",
                        "User",
                        null,
                        new ArrayList<FieldValue>(){{
                            add(new FieldValue("currentPassword", "notPassword"));
                            add(new FieldValue("newPassword", "newPassword"));
                        }}
                ));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}
