package com.chadrc.resourceapi.options;

import java.util.ArrayList;
import java.util.List;

public class PagingInfo {
    private Integer page = 0;
    private Integer count = 10;
    private List<PagingSort> sorts = new ArrayList<>();

    void setCount(Integer count) {
        this.count = count;
    }

    void setPage(Integer page) {
        this.page = page;
    }

    void setSorts(List<PagingSort> sorts) {
        this.sorts = sorts;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getPage() {
        return page;
    }
}
