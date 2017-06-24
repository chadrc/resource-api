package com.chadrc.resourceapi.options;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

class ListOptions {
    private String resourceName = null;
    private PagingInfo pagingInfo = new PagingInfo();

    String getResourceName() {
        return resourceName;
    }

    PagingInfo getPagingInfo() {
        return pagingInfo;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public void setCount(Integer count) {
        pagingInfo.setCount(count);
    }

    public void setPage(Integer page) {
        pagingInfo.setPage(page);
    }

    @SuppressWarnings("unchecked")
    public void setSort(String sortJson) throws IOException {
        if (StringUtils.isEmpty(sortJson)) {
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        SortParamShape sortsShape = mapper.readValue(sortJson, SortParamShape.class);
        pagingInfo.setSorts(sortsShape.sorts);
    }
}

class SortParamShape {
    List<PagingSort> sorts;
}