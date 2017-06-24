package com.chadrc.resourceapiexample.models;

import com.chadrc.resourceapi.annotations.ResourceModel;
import org.springframework.data.annotation.Id;

@ResourceModel
public class User {

    @Id
    private String id;

    private String firstName;

    private String lastName;

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
