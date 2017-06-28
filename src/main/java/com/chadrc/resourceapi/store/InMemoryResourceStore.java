package com.chadrc.resourceapi.store;

import com.chadrc.resourceapi.controller.PagingInfo;
import com.chadrc.resourceapi.service.ResourcePage;
import org.springframework.stereotype.Service;

@Service
public class InMemoryResourceStore implements ResourceStore {

    public InMemoryResourceStore() {

    }

    @Override
    public void saveNew(Class resourceType, Object obj) {

    }

    @Override
    public Object getById(Class resourceType, String id) {
        return null;
    }

    @Override
    public ResourcePage getList(Class c, PagingInfo pagingInfo) {
        return null;
    }
}
