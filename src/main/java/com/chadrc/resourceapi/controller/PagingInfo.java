package com.chadrc.resourceapi.controller;

import java.util.ArrayList;
import java.util.List;

public class PagingInfo {
    private Integer page = 0;
    private Integer count = 10;
    private List<PagingSort> sort = new ArrayList<>();

    void setCount(Integer count) {
        this.count = count;
    }

    void setPage(Integer page) {
        this.page = page;
    }

    void setSort(List<PagingSort> sort) {
        this.sort = sort;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getPage() {
        return page;
    }

    public List<PagingSort> getSort() {
        return new ArrayList<>(sort);
    }
}
