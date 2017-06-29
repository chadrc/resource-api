package com.chadrc.resourceapi.domain;

import com.chadrc.resourceapi.service.ResourceModel;
import org.springframework.stereotype.Component;

@Component
public class User implements ResourceModel {

    private String firstName;
    private String lastName;

    public User() {

    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
