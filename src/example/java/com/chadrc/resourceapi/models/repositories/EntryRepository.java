package com.chadrc.resourceapi.models.repositories;

import com.chadrc.resourceapi.models.Entry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EntryRepository extends MongoRepository<Entry, ObjectId> {
}
