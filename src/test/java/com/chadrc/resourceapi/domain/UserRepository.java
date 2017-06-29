package com.chadrc.resourceapi.domain;

import com.chadrc.resourceapi.annotations.ResourceClass;
import com.chadrc.resourceapi.store.ResourceRepository;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
@ResourceClass(User.class)
public interface UserRepository extends MongoRepository<User, ObjectId>, ResourceRepository<User, ObjectId> {

    @Override
    default Serializable convertId(String id) {
        return new ObjectId(id);
    }
}
