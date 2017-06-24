package com.chadrc.resourceapi.services;

import com.chadrc.resourceapi.options.PagingInfo;
import org.springframework.stereotype.Service;

@Service
public interface PersistenceService {

    void saveNew(Class resourceType, Object obj);

    Object getById(Class resourceType, String id);

    Object getList(Class c, PagingInfo pagingInfo);
}
