package com.chadrc.resourceapi.models;

import com.chadrc.resourceapi.basic.action.Action;
import com.chadrc.resourceapi.basic.crud.create.NoCreate;
import com.chadrc.resourceapi.basic.crud.update.NoUpdate;
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

    @NoCreate
    public Book(String title) {
        this.title = title;
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

    @NoUpdate
    public void setId(String id) {
        this.id = new ObjectId(id);
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

    @Action
    public Book capitalizeTitle() {
        this.title = this.title.toUpperCase();
        return this;
    }
}
