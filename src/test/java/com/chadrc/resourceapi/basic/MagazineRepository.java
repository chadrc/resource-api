package com.chadrc.resourceapi.basic;

import com.chadrc.resourceapi.models.Magazine;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MagazineRepository extends MongoRepository<Magazine, ObjectId>, ResourceRepository<Magazine, ObjectId> {
}
