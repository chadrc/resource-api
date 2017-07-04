package com.chadrc.resourceapi.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, creatorVisibility = JsonAutoDetect.Visibility.ANY)
public class PagingSort {
    private String field;
    private SortDirection direction = SortDirection.Acending;

    PagingSort() {

    }

    PagingSort(String field, SortDirection direction) {
        this.field = field;
        this.direction = direction;
    }

    public String getField() {
        return field;
    }

    public SortDirection getDirection() {
        return direction;
    }
}
