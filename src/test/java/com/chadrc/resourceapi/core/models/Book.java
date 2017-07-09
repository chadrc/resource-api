package com.chadrc.resourceapi.core.models;

import com.chadrc.resourceapi.core.ResourceModel;
import org.springframework.stereotype.Component;

@Component
public class Book implements ResourceModel {
    private String title = "";
    private String author = "";

    public Book() {

    }

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}
