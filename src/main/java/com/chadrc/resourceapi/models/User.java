package com.chadrc.resourceapi.models;

import com.chadrc.resourceapi.annotations.ResourceModel;
import org.springframework.data.annotation.Id;

@ResourceModel
public class User {

    @Id
    private String id;

    private String firstName;

    public User(String firstName) {
        this.firstName = firstName;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }
}
