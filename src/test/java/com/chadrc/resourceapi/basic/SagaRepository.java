package com.chadrc.resourceapi.basic;

import com.chadrc.resourceapi.models.Saga;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SagaRepository extends MongoRepository<Saga, ObjectId>, ResourceRepository<Saga, ObjectId> {
}
