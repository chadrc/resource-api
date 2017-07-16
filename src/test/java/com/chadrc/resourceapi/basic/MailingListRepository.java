package com.chadrc.resourceapi.basic;

import com.chadrc.resourceapi.models.MailingList;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailingListRepository extends MongoRepository<MailingList, ObjectId>, ResourceRepository<MailingList, ObjectId> {
}
