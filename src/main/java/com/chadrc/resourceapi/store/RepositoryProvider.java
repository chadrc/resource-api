package com.chadrc.resourceapi.store;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

@Component
public interface RepositoryProvider {
    PagingAndSortingRepository get(Class c);
}
