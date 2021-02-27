package com.jsr.pokemon.service;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;



public class PokemonServiceTest {

    private PokemonService sut;

    @Test
    public void getPokemonTest() {

        // expected webclient response
        Map<String, String> expectedPokemon = new HashMap();
        expectedPokemon.put("name", "pikachu");
        expectedPokemon.put("type", "electric");

        // expected response
        ClientResponse clientResponse = ClientResponse.create(HttpStatus.OK)
                .header("Content-Type", "application/json")
                .body(new JSONObject(expectedPokemon).toString()).build();

        ExchangeFunction shortCircuitingExchangeFunction = scef -> Mono.just(clientResponse);
        WebClient.Builder webClientBuilder = WebClient.builder().exchangeFunction(shortCircuitingExchangeFunction);


        String name = "pikachu";
        sut = new PokemonService(webClientBuilder);
        Map actualPokemon = sut.getPokemon(name);

        Assertions.assertEquals(expectedPokemon.get("name"),actualPokemon.get("name").toString());

    }

}
