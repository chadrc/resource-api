package com.chadrc.resourceapi.models;

import com.chadrc.resourceapi.basic.crud.create.NoCreate;
import com.chadrc.resourceapi.core.ResourceModel;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Magazine implements ResourceModel {

    @Id
    private ObjectId id;

    private String title;

    private List<String> categories;

    @NoCreate
    public Magazine() {

    }

    public Magazine(String title) {
        this.id = new ObjectId();
        this.title = title;
    }

    public Magazine(List<String> categories) {
        this.id = new ObjectId();
        this.categories = categories;
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

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
