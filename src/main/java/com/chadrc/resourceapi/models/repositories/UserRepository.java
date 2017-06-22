package com.chadrc.resourceapi.models.repositories;

import com.chadrc.resourceapi.models.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
}
