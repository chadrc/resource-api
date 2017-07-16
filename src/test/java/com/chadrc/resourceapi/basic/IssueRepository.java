package com.chadrc.resourceapi.basic;

import com.chadrc.resourceapi.models.Issue;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IssueRepository extends MongoRepository<Issue, ObjectId>, ResourceRepository<Issue, ObjectId> {
}
