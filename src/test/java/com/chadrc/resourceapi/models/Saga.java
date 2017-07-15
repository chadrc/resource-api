package com.chadrc.resourceapi.models;

import com.chadrc.resourceapi.core.ResourceModel;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Saga implements ResourceModel {

    @Id
    private ObjectId id;
    private String name = "";
    private List<Integer> books = new ArrayList<>();

    public Saga() {

    }

    public Saga(String name, List<Integer> books) {
        this.id = new ObjectId();
        this.name = name;
        this.books = books;
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

    public List<Integer> getBooks() {
        return books;
    }

    public void setBooks(List<Integer> books) {
        this.books = books;
    }
}
