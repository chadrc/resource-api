package com.chadrc.resourceapiexample.models.repositories;

import com.chadrc.resourceapiexample.models.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
}
