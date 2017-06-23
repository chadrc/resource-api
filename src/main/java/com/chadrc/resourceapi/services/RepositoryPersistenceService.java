package com.chadrc.resourceapi.services;

import com.chadrc.resourceapi.models.User;
import com.chadrc.resourceapi.models.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RepositoryPersistenceService implements PersistenceService {

    private final Map<Class, MongoRepository> repositoryMap = new HashMap<>();

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
        return repositoryMap.get(resourceType).findOne(id);
    }
}
