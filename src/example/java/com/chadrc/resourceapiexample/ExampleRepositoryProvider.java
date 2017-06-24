package com.chadrc.resourceapiexample;

import com.chadrc.resourceapi.store.RepositoryProvider;
import com.chadrc.resourceapiexample.models.User;
import com.chadrc.resourceapiexample.models.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class ExampleRepositoryProvider implements RepositoryProvider {

    private final UserRepository userRepository;

    @Autowired
    public ExampleRepositoryProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public PagingAndSortingRepository get(Class c) {
        if (User.class == c) {
            return userRepository;
        }
        return null;
    }

    @Override
    public Serializable convertId(String id) {
        return new ObjectId(id);
    }
}
