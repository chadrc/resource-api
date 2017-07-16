package com.chadrc.resourceapi.models;

import com.chadrc.resourceapi.basic.crud.create.NoCreate;
import com.chadrc.resourceapi.core.ResourceModel;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

@Component
public class Newspaper implements ResourceModel {

    @Id
    private ObjectId id;

    private String name;

    @NoCreate
    public Newspaper() {

    }

    public Newspaper(String name) {
        this.name = name;
    }

    public ObjectId objectId() {
        return id;
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
}
