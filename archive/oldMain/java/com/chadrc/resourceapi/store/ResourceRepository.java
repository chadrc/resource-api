package com.chadrc.resourceapi.store;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

@NoRepositoryBean
public interface ResourceRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {
    Serializable convertId(String id);
}
