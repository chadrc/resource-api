package com.chadrc.resourceapi.models;

import com.chadrc.resourceapi.core.ResourceModel;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

@Component
public class Book implements ResourceModel {

    @Id
    private ObjectId id;
    private String title = "";
    private String author = "";

    public Book() {

    }

    public Book(String title, String author) {
        this.id = new ObjectId();
        this.title = title;
        this.author = author;
    }

    public String getId() {
        return id.toString();
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}
