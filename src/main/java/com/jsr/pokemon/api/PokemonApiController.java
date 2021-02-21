package com.jsr.pokemon.api;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsr.pokemon.model.Pokemon;
import com.jsr.pokemon.service.PokemonService;
import io.swagger.annotations.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpStatusCodeException;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-02-21T00:02:36.778Z")

@Controller
public class PokemonApiController implements PokemonApi {

    private static final Logger log = LoggerFactory.getLogger(PokemonApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    public PokemonApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Autowired
    private PokemonService pokemonService;

    public ResponseEntity<Pokemon> getPokemonByName(@ApiParam(value = "Name of Pokemon to return",required=true) @PathVariable("pokemonName") String pokemonName) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                ResponseEntity<Pokemon> responseEntity = null;
                Map<String,Object> pokeRequest = pokemonService.getPokemon(pokemonName);
                Pokemon pokemon = objectMapper.convertValue(pokeRequest, Pokemon.class);

                return responseEntity.ok(pokemon);

            } catch (HttpStatusCodeException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Pokemon>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Pokemon>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Pokemon>> getPokemonList() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                ResponseEntity<List<Pokemon>> responseEntity = null;
                Map<String,Object> pokeRequest = pokemonService.getPokemonList();
                List<Object> objectList = (List<Object>) pokeRequest.get("results");

                List<Pokemon> pokemonList = new ArrayList<>();

                for (Object item : objectList){
                    Pokemon temp = objectMapper.convertValue(item, Pokemon.class);
                    temp.setId(objectList.indexOf(item));
                    pokemonList.add(temp);
                }

                return responseEntity.ok(pokemonList);

            } catch (HttpStatusCodeException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Pokemon>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Pokemon>>(HttpStatus.NOT_IMPLEMENTED);
    }

}
