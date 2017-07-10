package com.chadrc.resourceapi.basic.crud;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

@NoRepositoryBean
public interface ResourceRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {
}
