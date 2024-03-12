package com.mohey.reactivedemo.model;


import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

public record GameOfThronesDTO(String id, String character, String city, String dragon, String house, String quote) {

}
