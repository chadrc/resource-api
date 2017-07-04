package com.chadrc.resourceapiunit.store;

import com.chadrc.resourceapi.domain.User;
import com.chadrc.resourceapi.store.ResourceRepository;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public class TestRepository implements ResourceRepository<User, ObjectId> {
    @Override
    public Serializable convertId(String id) {
        return new ObjectId(id);
    }

    @Override
    public Iterable<User> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends User> S save(S entity) {
        return null;
    }

    @Override
    public <S extends User> Iterable<S> save(Iterable<S> entities) {
        return null;
    }

    @Override
    public User findOne(ObjectId objectId) {
        return null;
    }

    @Override
    public boolean exists(ObjectId objectId) {
        return false;
    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }

    @Override
    public Iterable<User> findAll(Iterable<ObjectId> objectIds) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(ObjectId objectId) {

    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public void delete(Iterable<? extends User> entities) {

    }

    @Override
    public void deleteAll() {

    }
}