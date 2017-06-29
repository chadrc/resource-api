package com.chadrc.resourceapi.controller;

import com.chadrc.resourceapi.domain.User;
import com.chadrc.resourceapi.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ResourceControllerProxy {

    private ResourceController resourceController;

    @Autowired
    public void setResourceController(ResourceService resourceService) {
        this.resourceController = new ResourceController(resourceService);
    }

    public ResourceController getResourceController() {
        return resourceController;
    }

    public User addUser(String firstName, String lastName) {
        List<FieldValue> fieldValues = new ArrayList<FieldValue>(){{
            add(new FieldValue("firstName", firstName));
            add(new FieldValue("lastName", lastName));
        }};

        ResponseEntity<Object> responseEntity = resourceController.create(new CreateOptions("User", fieldValues));
        return (User) responseEntity.getBody();
    }
}
