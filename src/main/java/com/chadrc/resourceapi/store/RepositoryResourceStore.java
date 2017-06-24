package com.chadrc.resourceapi.store;

import com.chadrc.resourceapi.models.User;
import com.chadrc.resourceapi.models.repositories.UserRepository;
import com.chadrc.resourceapi.controller.PagingInfo;
import com.chadrc.resourceapi.controller.PagingSort;
import com.chadrc.resourceapi.controller.SortDirection;
import com.chadrc.resourceapi.service.ResourcePage;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RepositoryResourceStore implements ResourceStore {

    private final Map<Class, PagingAndSortingRepository> repositoryMap = new HashMap<>();

    @Autowired
    public RepositoryResourceStore(UserRepository userRepository) {
        repositoryMap.put(User.class, userRepository);
    }

    @Override
    public void saveNew(Class resourceType, Object obj) {
        repositoryMap.get(resourceType).save(obj);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object getById(Class resourceType, String id) {
        return repositoryMap.get(resourceType).findOne(new ObjectId(id));
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
        Page page = repositoryMap.get(resourceType).findAll(pageRequest);
        return new ResourcePage(page.getContent(), page.getTotalElements(), page.getSize(), page.isFirst(), page.isLast());
    }

    private Sort.Direction convertDirection(SortDirection sortDirection) {
        return sortDirection == SortDirection.Acending ? Sort.Direction.ASC : Sort.Direction.DESC;
    }
}