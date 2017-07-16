package com.chadrc.resourceapi.basic;

import com.chadrc.resourceapi.models.MagazineIssue;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MagazineIssueRepository extends MongoRepository<MagazineIssue, ObjectId>, ResourceRepository<MagazineIssue, ObjectId> {
}
