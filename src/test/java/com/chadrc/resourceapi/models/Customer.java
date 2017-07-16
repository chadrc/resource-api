package com.chadrc.resourceapi.models;

import com.chadrc.resourceapi.basic.crud.create.NoCreate;
import com.chadrc.resourceapi.core.ResourceModel;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

@Component
public class Customer implements ResourceModel {

    @Id
    private ObjectId id;

    private String name;

    private Address address;

    @NoCreate
    public Customer() {

    }

    public Customer(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public String getId() {
        return id.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
