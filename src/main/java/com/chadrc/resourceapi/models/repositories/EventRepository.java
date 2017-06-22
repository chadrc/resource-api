package com.chadrc.resourceapi.models.repositories;

import com.chadrc.resourceapi.models.Event;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, ObjectId> {
}
