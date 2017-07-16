package com.chadrc.resourceapi.basic;

import com.chadrc.resourceapi.models.Customer;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, ObjectId>, ResourceRepository<Customer, ObjectId> {
}
