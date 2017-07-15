package com.chadrc.resourceapi.basic.crud.read;

import java.util.List;

public class GetRequest {
    private String id;
    private Integer page;
    private Integer count;
    private List<GetSort> sort;

    public GetRequest() {

    }

    public GetRequest(String id) {
        this.id = id;
    }

    public GetRequest(Integer page, Integer count) {
        this.page = page;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<GetSort> getSort() {
        return sort;
    }

    public void setSort(List<GetSort> sort) {
        this.sort = sort;
    }
}
