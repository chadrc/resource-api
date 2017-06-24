package com.chadrc.resourceapi.services;

import java.util.List;

public class ResourcePage {
    private List<Object> resources;
    private long total;
    private int pageSize;
    private boolean first;
    private boolean last;

    public ResourcePage(List<Object> resources, long total, int pageSize, boolean first, boolean last) {
        this.resources = resources;
        this.total = total;
        this.pageSize = pageSize;
        this.first = first;
        this.last = last;
    }

    public List getResources() {
        return resources;
    }

    public long getTotal() {
        return total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public boolean isFirst() {
        return first;
    }

    public boolean isLast() {
        return last;
    }
}
