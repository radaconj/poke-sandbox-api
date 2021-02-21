package com.jsr.pokemon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class PokemonService {

    @Autowired
    private WebClient.Builder webClientBuilder;


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
