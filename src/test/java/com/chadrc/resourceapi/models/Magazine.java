package com.chadrc.resourceapi.models;

import com.chadrc.resourceapi.basic.crud.create.NoCreate;
import com.chadrc.resourceapi.core.ResourceModel;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

@Component
public class Magazine implements ResourceModel {

    @Id
    private ObjectId id;

    private String title;

    @NoCreate
    public Magazine() {

    }

    public Magazine(String title) {

    }

    public String getId() {
        return id.toString();
    }

    public ObjectId objectId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
