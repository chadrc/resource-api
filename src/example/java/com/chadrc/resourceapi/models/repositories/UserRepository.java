package com.chadrc.resourceapi.models.repositories;

import com.chadrc.resourceapi.annotations.ResourceClass;
import com.chadrc.resourceapi.models.User;
import org.bson.types.ObjectId;

@ResourceClass(User.class)
public interface UserRepository extends MongoResourceRepository<User, ObjectId> {
}
