package com.chadrc.resourceapi.models;

import com.chadrc.resourceapi.core.ResourceModel;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class MailingList implements ResourceModel {

    @Id
    private ObjectId id;

    private List<Address> addresses;

    public MailingList() {

    }

    public MailingList(List<Address> addresses) {
        this.id = new ObjectId();
        this.addresses = new ArrayList<>();
        for (Address address : addresses) {
            if (!StringUtils.isEmpty(address.getCity())) {
                this.addresses.add(address);
            }
        }
    }

    public String getId() {
        return id.toString();
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
