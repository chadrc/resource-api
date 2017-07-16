package com.chadrc.resourceapi.basic;

import com.chadrc.resourceapi.models.Newspaper;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NewspaperRepository extends MongoRepository<Newspaper, ObjectId>, ResourceRepository<Newspaper, ObjectId> {
}
