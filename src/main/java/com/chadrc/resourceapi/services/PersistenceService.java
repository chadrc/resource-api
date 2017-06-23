package com.chadrc.resourceapi.services;

import org.springframework.stereotype.Service;

@Service
public interface PersistenceService {

    void saveNew(Object obj);
}
