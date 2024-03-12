package com.mohey.reactivedemo.service;


import com.github.javafaker.Faker;

import com.mohey.reactivedemo.model.GameOfThrones;
import com.mohey.reactivedemo.repository.GameOfThronesRepo;
import jakarta.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GameOfThronesService {

    private final GameOfThronesRepo repo;
    private final Faker faker;

    public GameOfThronesService(GameOfThronesRepo gameOfThronesRepo) {
        this.repo = gameOfThronesRepo;
        this.faker = Faker.instance();
    }

    @PostConstruct
    public void init() {
        repo.count()
                .flatMapMany(aLong -> {
                            if (aLong != 100) {
                                return Flux
                                        .range(0, 1000)
                                        .map(integer -> new GameOfThrones(ObjectId.get(),
                                                faker.gameOfThrones().character(),
                                                faker.gameOfThrones().city(),
                                                faker.gameOfThrones().dragon(),
                                                faker.gameOfThrones().house(),
                                                faker.gameOfThrones().quote()))
                                        .collectList()
                                        .flatMapMany(repo::saveAll);

                            }

                            return Mono.empty();
                        }

                )
                .collectList()
                .subscribe();

    }
}
