package com.chadrc.resourceapi.models;

import com.chadrc.resourceapi.service.ResourceModel;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

@Component
public class User implements ResourceModel {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    public User() {}

    public User(String firstName) {
        this.firstName = firstName;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public static User register(String firstName, String lastName) {
        User user = new User(firstName);
        user.lastName = lastName;
        return user;
    }
}
