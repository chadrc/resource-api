package com.chadrc.resourceapi.services;

import com.chadrc.resourceapi.models.User;
import com.chadrc.resourceapi.models.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RepositoryPersistenceService implements PersistenceService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveNew(Object obj) {
        if (obj instanceof User) {
            User user = (User) obj;
            userRepository.save(user);
        }
    }
}
