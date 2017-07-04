package com.chadrc.resourceapi.store;

import com.chadrc.resourceapi.controller.PagingInfo;
import com.chadrc.resourceapi.service.ResourcePage;
import org.springframework.stereotype.Service;

@Service
public interface ResourceStore {

    void saveNew(Class resourceType, Object obj);

    Object getById(Class resourceType, String id);

    ResourcePage getList(Class c, PagingInfo pagingInfo);
}
