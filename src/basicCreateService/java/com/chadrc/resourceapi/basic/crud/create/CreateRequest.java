package com.chadrc.resourceapi.basic.crud.create;

import java.util.ArrayList;
import java.util.List;

public class CreateRequest {
    private List<CreateParameter> paramValues = new ArrayList<>();

    public CreateRequest() {

    }

    public CreateRequest(List<CreateParameter> paramValues) {
        this.paramValues = paramValues;
    }

    public List<CreateParameter> getParamValues() {
        return paramValues;
    }

    public void setParamValues(List<CreateParameter> paramValues) {
        this.paramValues = paramValues;
    }
}
