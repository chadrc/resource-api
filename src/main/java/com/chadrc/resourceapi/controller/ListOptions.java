package com.chadrc.resourceapi.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

class ListOptions {
    private String resourceName = null;
    private PagingInfo pagingInfo = new PagingInfo();

    public ListOptions() {

    }

    ListOptions(String resourceName, PagingInfo pagingInfo) {
        this.resourceName = resourceName;
        this.pagingInfo = pagingInfo == null ? new PagingInfo() : pagingInfo;
    }

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
        String modified = "{\"sort\":" + sortJson + "}";
        ObjectMapper mapper = new ObjectMapper();
        SortParamShape sortsShape = mapper.readValue(modified, SortParamShape.class);
        pagingInfo.setSort(sortsShape.sort);
    }
}

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, creatorVisibility = JsonAutoDetect.Visibility.ANY)
class SortParamShape {
    List<PagingSort> sort;
}