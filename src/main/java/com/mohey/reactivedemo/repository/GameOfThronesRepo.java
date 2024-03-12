package com.mohey.reactivedemo.repository;


import com.mohey.reactivedemo.model.GameOfThrones;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameOfThronesRepo extends ReactiveMongoRepository<GameOfThrones, ObjectId> {
}
