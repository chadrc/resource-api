package com.chadrc.resourceapi.services;

import com.chadrc.resourceapi.models.User;
import com.chadrc.resourceapi.models.repositories.UserRepository;
import com.chadrc.resourceapi.options.PagingInfo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RepositoryPersistenceService implements PersistenceService {

    private final Map<Class, PagingAndSortingRepository> repositoryMap = new HashMap<>();

    @Autowired
    public RepositoryPersistenceService(UserRepository userRepository) {
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
        PageRequest pageRequest = new PageRequest(pagingInfo.getPage(), pagingInfo.getCount());
        return repositoryMap.get(resourceType).findAll(pageRequest);
    }
}
