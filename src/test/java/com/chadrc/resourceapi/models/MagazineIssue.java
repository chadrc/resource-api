package com.chadrc.resourceapi.models;

import com.chadrc.resourceapi.basic.crud.create.FromId;
import com.chadrc.resourceapi.basic.crud.create.NoCreate;
import com.chadrc.resourceapi.core.ResourceModel;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class MagazineIssue implements ResourceModel {

    @Id
    private ObjectId id;

    private ObjectId magazineId;

    private Calendar issueDate;

    @NoCreate
    public MagazineIssue() {

    }

    public MagazineIssue(@FromId Magazine magazine) {
        this.magazineId = magazine.objectId();
        issueDate = Calendar.getInstance();
    }

    public String getId() {
        return this.id.toString();
    }

    public ObjectId objectId() {
        return this.id;
    }

    public String getMagazineId() {
        return this.magazineId.toString();
    }

    public Calendar getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Calendar issueDate) {
        this.issueDate = issueDate;
    }
}
