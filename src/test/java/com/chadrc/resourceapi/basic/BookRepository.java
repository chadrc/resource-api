package com.chadrc.resourceapi.basic;

import com.chadrc.resourceapi.basic.crud.get.ResourceRepository;
import com.chadrc.resourceapi.models.Book;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<Book, ObjectId>, ResourceRepository<Book, ObjectId> {
}
