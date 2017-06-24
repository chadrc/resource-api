package com.chadrc.resourceapi.services;

import com.chadrc.resourceapi.models.User;
import com.chadrc.resourceapi.models.repositories.UserRepository;
import com.chadrc.resourceapi.options.PagingInfo;
import com.chadrc.resourceapi.options.PagingSort;
import com.chadrc.resourceapi.options.SortDirection;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Object getList(Class resourceType, PagingInfo pagingInfo) {
        List<Sort.Order> orders = new ArrayList<>();
        for (PagingSort sort : pagingInfo.getSort()) {
            orders.add(new Sort.Order(convertDirection(sort.getDirection()), sort.getField()));
        }
        Sort sort = new Sort(orders);
        PageRequest pageRequest = new PageRequest(pagingInfo.getPage(), pagingInfo.getCount(), sort);
        return repositoryMap.get(resourceType).findAll(pageRequest);
    }

    private Sort.Direction convertDirection(SortDirection sortDirection) {
        return sortDirection == SortDirection.Acending ? Sort.Direction.ASC : Sort.Direction.DESC;
    }
}
