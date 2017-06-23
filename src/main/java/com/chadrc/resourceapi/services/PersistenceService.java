package com.chadrc.resourceapi.services;

import org.springframework.stereotype.Service;

@Service
public interface PersistenceService {

    void persist(Object obj);
}
