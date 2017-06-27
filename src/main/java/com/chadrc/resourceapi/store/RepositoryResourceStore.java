package com.chadrc.resourceapi.store;

import com.chadrc.resourceapi.annotations.ResourceClass;
import com.chadrc.resourceapi.controller.PagingInfo;
import com.chadrc.resourceapi.controller.PagingSort;
import com.chadrc.resourceapi.controller.SortDirection;
import com.chadrc.resourceapi.service.ResourcePage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RepositoryResourceStore implements ResourceStore {

    private static Logger log = Logger.getLogger(RepositoryResourceStore.class);

    private final Map<Class, ResourceRepository> repositoryByClass;

    @Autowired
    public RepositoryResourceStore(List<ResourceRepository> resourceRepositories) {
        this.repositoryByClass = new HashMap<>();
        for (ResourceRepository resourceRepository : resourceRepositories) {
            Class repositoryClass = resourceRepository.getClass();
            Type[] supers = repositoryClass.getGenericInterfaces();
            Class firstSuper = ((Class) supers[0]);
            ResourceClass resourceClass = (ResourceClass) firstSuper.getAnnotation(ResourceClass.class);

            if (resourceClass == null) {
                throw new IllegalArgumentException("ResourceRepository must have ResourceClass annotation: " + repositoryClass.getCanonicalName());
            }

            Class modelClass = resourceClass.value();
            log.info("Registering " + firstSuper.getCanonicalName() + " as repository for " + modelClass.getCanonicalName());
            repositoryByClass.put(modelClass, resourceRepository);
        }
    }

    @Override
    public void saveNew(Class resourceType, Object obj) {
        repositoryByClass.get(resourceType).save(obj);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object getById(Class resourceType, String id) {
        ResourceRepository resourceRepository = repositoryByClass.get(resourceType);
        return resourceRepository.findOne(resourceRepository.convertId(id));
    }

    @Override
    @SuppressWarnings("unchecked")
    public ResourcePage getList(Class resourceType, PagingInfo pagingInfo) {
        List<Sort.Order> orders = new ArrayList<>();
        for (PagingSort sort : pagingInfo.getSort()) {
            orders.add(new Sort.Order(convertDirection(sort.getDirection()), sort.getField()));
        }
        PageRequest pageRequest;
        if (orders.size() > 0) {
            pageRequest = new PageRequest(pagingInfo.getPage(), pagingInfo.getCount(), new Sort(orders));
        } else {
            pageRequest = new PageRequest(pagingInfo.getPage(), pagingInfo.getCount());
        }
        Page page = repositoryByClass.get(resourceType).findAll(pageRequest);
        return new ResourcePage(page.getContent(), page.getTotalElements(), page.getSize(), page.isFirst(), page.isLast());
    }

    private Sort.Direction convertDirection(SortDirection sortDirection) {
        return sortDirection == SortDirection.Acending ? Sort.Direction.ASC : Sort.Direction.DESC;
    }
}
