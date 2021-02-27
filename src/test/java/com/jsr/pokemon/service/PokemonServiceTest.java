package com.jsr.pokemon.service;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;


public class PokemonServiceTest {

    private PokemonService sut;

    @Mock
    ExchangeFunction exchangeFunction;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        // ShortCircuiting desired Response
        WebClient.Builder webClientBuilder = WebClient.builder().exchangeFunction(exchangeFunction);
        // new service class
        sut = new PokemonService(webClientBuilder);

    }

    @Test
    void getPokemonTest2() {

        // expected test method response
        Map<String, String> expectedPokemon = new HashMap();
        expectedPokemon.put("name", "pikachu");
        expectedPokemon.put("type", "electric");

        // expected endpoint response
        ClientResponse clientResponse = ClientResponse.create(HttpStatus.OK)
                .header("Content-Type", "application/json")
                .body(new JSONObject(expectedPokemon).toString()).build();

        //ExchangeFunction stub
        Mockito.when(exchangeFunction.exchange(any(ClientRequest.class))).thenReturn(Mono.just(clientResponse));

        // test parameter
        String name = "pikachu";
        // calling class to test
        Map actualPokemon = sut.getPokemon(name);

        // verify
        Assertions.assertEquals(expectedPokemon.get("name"),actualPokemon.get("name").toString());

    }

    @Disabled("Alternative implemetation:")
    @Test
    void getPokemonTest() {

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
