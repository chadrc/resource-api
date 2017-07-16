package com.chadrc.resourceapi.basic;

import com.chadrc.resourceapi.models.BookOrder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookOrderRepository extends MongoRepository<BookOrder, ObjectId>, ResourceRepository<BookOrder, ObjectId> {
}
