package com.chadrc.resourceapiexample.models.repositories;

import com.chadrc.resourceapiexample.models.Event;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, ObjectId> {
}
