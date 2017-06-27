package com.chadrc.resourceapiexample.models.repositories;

import com.chadrc.resourceapi.store.ResourceRepository;
import com.chadrc.resourceapiexample.models.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId>, ResourceRepository<User, ObjectId> {
}
