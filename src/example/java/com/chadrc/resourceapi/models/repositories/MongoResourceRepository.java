package com.chadrc.resourceapi.models.repositories;

import com.chadrc.resourceapi.store.ResourceRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface MongoResourceRepository<T, ID extends Serializable> extends MongoRepository<T, ID>, ResourceRepository<T, ID> {
    @Override
    default Serializable convertId(String id) {
        return new ObjectId(id);
    }
}
