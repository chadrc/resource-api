package com.chadrc.resourceapi.basic.crud.post;

import java.util.ArrayList;
import java.util.List;

public class CreateRequest {
    private List<Object> paramValues = new ArrayList<>();

    public CreateRequest() {

    }

    public CreateRequest(List<Object> paramValues) {
        this.paramValues = paramValues;
    }

    public List<Object> getParamValues() {
        return paramValues;
    }

    public void setParamValues(List<Object> paramValues) {
        this.paramValues = paramValues;
    }
}
