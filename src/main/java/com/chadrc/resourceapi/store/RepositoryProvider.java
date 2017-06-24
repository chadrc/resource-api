package com.chadrc.resourceapi.store;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public interface RepositoryProvider {
    Serializable convertId(String id);
    PagingAndSortingRepository get(Class c);
}
