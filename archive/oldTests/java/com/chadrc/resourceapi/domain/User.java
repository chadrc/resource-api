package com.chadrc.resourceapi.domain;

import com.chadrc.resourceapi.service.Action;
import com.chadrc.resourceapi.service.ResourceModel;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class User implements ResourceModel {

    @Id
    private String id;

    private String firstName;
    private String lastName;

    private boolean signedForNewsletter = false;

    private String password = "password";

    public User() {

    }

    public User(String firstName, String lastName) throws Exception {
        if ("Invalid".equals(firstName) && "Name".equals(lastName)) {
            throw new InvalidName();
        }
        this.firstName = firstName;
        this.lastName = lastName;
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public boolean getSignedForNewletter() {
        return signedForNewsletter;
    }

    @Action
    public void signUpForNewsletter() {
        signedForNewsletter = true;
    }

    @Action
    public void changePassword(String oldPassword, String newPassword) throws Exception {
        if (!password.equals(oldPassword)) {
            throw new InvalidPassword();
        }
        password = newPassword;
    }
}
