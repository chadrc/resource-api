package com.chadrc.resourceapi.models;

import com.chadrc.resourceapi.basic.FromId;
import com.chadrc.resourceapi.basic.crud.create.NoCreate;
import com.chadrc.resourceapi.core.ResourceModel;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookOrder implements ResourceModel {
    @Id
    private ObjectId id;
    private ObjectId customerId;
    private List<ObjectId> bookIds;

    @NoCreate
    public BookOrder() {
        bookIds = new ArrayList<>();
    }

    public BookOrder(@FromId List<Book> books) {
        bookIds = new ArrayList<>();
        for (Book book : books) {
            bookIds.add(book.objectId());
        }
    }

    public String getId() {
        return id.toString();
    }

    public ObjectId getCustomerId() {
        return customerId;
    }

    public void setCustomerId(ObjectId customerId) {
        this.customerId = customerId;
    }

    public List<ObjectId> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<ObjectId> bookIds) {
        this.bookIds = bookIds;
    }

    public void setBooks(@FromId List<Book> books) {
        for (Book book : books) {
            bookIds.add(book.objectId());
        }
    }
}
