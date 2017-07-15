package com.chadrc.resourceapi.basic.crud.read;

public class GetResult {
    private Object records;

    GetResult(Object records) {
        this.records = records;
    }

    public Object getRecords() {
        return records;
    }
}
