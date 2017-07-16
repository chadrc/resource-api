package com.chadrc.resourceapi.models;

import com.chadrc.resourceapi.basic.FromId;
import com.chadrc.resourceapi.basic.crud.create.NoCreate;
import com.chadrc.resourceapi.core.ResourceModel;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class Issue implements ResourceModel {

    @Id
    private ObjectId id;

    private ObjectId issuableId;

    private Calendar issueDate;

    @NoCreate
    public Issue() {
        this.id = new ObjectId();
    }

    public Issue(@FromId(mustExist = false) Magazine magazine) {
        this.issuableId = magazine == null ? new ObjectId() : magazine.objectId();
        issueDate = Calendar.getInstance();
    }

    public Issue(@FromId Newspaper newspaper) {
        this.issuableId = newspaper.objectId();
        issueDate = Calendar.getInstance();
    }

    public Issue(@FromId Catalog catalog) {

    }

    public void setIssuable(@FromId(mustExist = false) Magazine magazine) {
        if (magazine != null) {
            this.issuableId = magazine.objectId();
        }
    }

    public void setIssuable(@FromId Newspaper newspaper) {
        this.issuableId = newspaper.objectId();
    }

    public String getId() {
        return this.id.toString();
    }

    public ObjectId objectId() {
        return this.id;
    }

    public String getIssuableId() {
        return this.issuableId.toString();
    }

    public Calendar getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Calendar issueDate) {
        this.issueDate = issueDate;
    }
}
