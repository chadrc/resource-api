package com.chadrc.resourceapiexample.models.repositories;

import com.chadrc.resourceapi.annotations.ResourceClass;
import com.chadrc.resourceapiexample.models.User;
import org.bson.types.ObjectId;

@ResourceClass(User.class)
public interface UserRepository extends MongoResourceRepository<User, ObjectId> {
}
