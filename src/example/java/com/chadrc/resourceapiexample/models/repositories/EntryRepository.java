package com.chadrc.resourceapiexample.models.repositories;

import com.chadrc.resourceapiexample.models.Entry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EntryRepository extends MongoRepository<Entry, ObjectId> {
}
