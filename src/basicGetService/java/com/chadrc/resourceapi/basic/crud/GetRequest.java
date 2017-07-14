package com.chadrc.resourceapi.basic.crud;

import java.util.ArrayList;
import java.util.List;

public class GetRequest {
    private List<String> ids;

    public GetRequest() {
        this(new ArrayList<>());
    }

    public GetRequest(String id) {
        this(new ArrayList<String>(){{add(id);}});
    }

    public GetRequest(List<String> ids) {
        this.ids = ids;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
