package com.mohey.reactivedemo.model;


import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="GameOfThrones")
public record GameOfThrones(@BsonId ObjectId id, String character, String city, String dragon, String house, String quote) {

}
