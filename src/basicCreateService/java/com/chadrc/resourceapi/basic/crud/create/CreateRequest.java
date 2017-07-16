package com.chadrc.resourceapi.basic.crud.create;

import com.chadrc.resourceapi.basic.RequestParameter;

import java.util.ArrayList;
import java.util.List;

public class CreateRequest {
    private List<RequestParameter> paramValues = new ArrayList<>();

    public CreateRequest() {

    }

    public CreateRequest(List<RequestParameter> paramValues) {
        this.paramValues = paramValues;
    }

    public List<RequestParameter> getParamValues() {
        return paramValues;
    }

    public void setParamValues(List<RequestParameter> paramValues) {
        this.paramValues = paramValues;
    }
}
