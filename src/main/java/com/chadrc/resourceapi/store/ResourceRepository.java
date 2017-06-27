package com.chadrc.resourceapi.store;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

public interface ResourceRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {
}
