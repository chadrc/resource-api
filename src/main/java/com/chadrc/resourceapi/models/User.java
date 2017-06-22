package com.chadrc.resourceapi.models;

import com.chadrc.resourceapi.annotations.ResourceModel;

@ResourceModel
public class User {
    private String firstName;

    public User(String firstName) {
        this.firstName = firstName;
    }
}
