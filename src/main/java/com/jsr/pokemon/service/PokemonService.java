package com.jsr.pokemon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Service
public class PokemonService {


    private WebClient.Builder webClientBuilder;

    @Autowired
    public PokemonService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Map getPokemonList(){

        Map pokemonList = webClientBuilder.build()
                .get()
                .uri("https://pokeapi.co/api/v2/pokemon")
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return pokemonList;
    }

    public Map getPokemon(String name ){

        Map pokemon = webClientBuilder.build()
                .get()
                .uri("https://pokeapi.co/api/v2/pokemon/"+ name)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return pokemon;
    }

    public Map getTypeList(){

        Map pokemonList = webClientBuilder.build()
                .get()
                .uri("https://pokeapi.co/api/v2/type")
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return pokemonList;
    }


}
