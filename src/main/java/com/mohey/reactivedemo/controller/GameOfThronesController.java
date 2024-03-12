package com.mohey.reactivedemo.controller;

import com.mohey.reactivedemo.model.GameOfThrones;
import com.mohey.reactivedemo.model.GameOfThronesDTO;
import com.mohey.reactivedemo.repository.GameOfThronesRepo;
import com.mohey.reactivedemo.service.GameOfThronesService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@RestController
@RequestMapping
public class GameOfThronesController {
    private final GameOfThronesRepo gameOfThronesRepo;
    private final GameOfThronesService gameOfThronesService;

    public GameOfThronesController(GameOfThronesRepo gameOfThronesRepo, GameOfThronesService gameOfThronesService) {
        this.gameOfThronesRepo = gameOfThronesRepo;
        this.gameOfThronesService = gameOfThronesService;
    }


    @GetMapping(path = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<GameOfThrones> getGameOfThrones() throws InterruptedException {
//        System.out.println("Thread.activeCount() = " + Thread.activeCount());
        return gameOfThronesRepo.findAll();
    }

    @GetMapping(path = "/tes/2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<GameOfThrones> getGameOfThrones2() throws InterruptedException {
//        System.out.println("Thread.activeCount() = " + Thread.activeCount());
        return gameOfThronesRepo.findAll();
    }

    @GetMapping(path = "/map", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<GameOfThronesDTO> getGameOfThronesMap() {
        return gameOfThronesRepo.findAll()
                .log("/beforemap")
                .map(gameOfThrones -> new GameOfThronesDTO(gameOfThrones.id().toHexString(), gameOfThrones.character(), gameOfThrones.city(), gameOfThrones.dragon(), gameOfThrones.house(), gameOfThrones.quote()))
                .subscribeOn(Schedulers.boundedElastic())
                .log("/map");
    }

    @GetMapping(path = "/bulk-head", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<GameOfThrones> getGameOfThronesBulkHead() {
        return gameOfThronesRepo.findAll()
                .delayElements(Duration.ofMinutes(1L))
                .log();
    }

    @GetMapping("/client")
    public Flux<GameOfThrones> getGameOfThronesClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build()
                .get()
                .uri("/test")
                .retrieve()
                .bodyToFlux(GameOfThrones.class)
                ;
    }

}
