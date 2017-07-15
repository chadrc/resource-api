package com.chadrc.resourceapi.models;

import com.chadrc.resourceapi.core.Resource;
import com.chadrc.resourceapi.core.ResourceModel;
import com.chadrc.resourceapi.core.ResourceServiceThrowable;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class Book implements ResourceModel {

    @Id
    private ObjectId id;
    private String title = "";
    private String author = "";

    public Book() {

    }

    public Book(String title, String author) throws ResourceServiceThrowable {
        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(author)) {
            throw Resource.badRequest();
        }
        this.id = new ObjectId();
        this.title = title;
        this.author = author;
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

    public void setTitle(String title) throws ResourceServiceThrowable {
        if (StringUtils.isEmpty(title)) {
            throw Resource.badRequest();
        }
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
