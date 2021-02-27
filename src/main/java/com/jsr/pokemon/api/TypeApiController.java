package com.jsr.pokemon.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsr.pokemon.api.TypeApi;
import com.jsr.pokemon.model.Pokemon;
import com.jsr.pokemon.service.PokemonService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.HttpStatusCodeException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-02-21T00:02:36.778Z")

@Controller
public class TypeApiController implements TypeApi {

    private static final Logger log = LoggerFactory.getLogger(TypeApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    public TypeApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Autowired
    private PokemonService pokemonService;

    public ResponseEntity<List<String>> getTypes() {
        String accept = request.getHeader("Accept");
        if (accept != null && (accept.contains("application/json") || accept.contains("application/xml"))) {
            try {
                ResponseEntity<List<String>> responseEntity = null;
                Map<String,Object> typeRequest = pokemonService.getTypeList();
                List<Object> objectList = (List<Object>) typeRequest.get("results");

                List<String> typeList = new ArrayList<>();

                for (Object item : objectList){
                    Map<String,String> temp = objectMapper.convertValue(item, Map.class);
                    typeList.add(temp.get("name"));
                }

                return responseEntity.ok(typeList);

            } catch (HttpStatusCodeException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<String>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<String>>(HttpStatus.NOT_IMPLEMENTED);
    }

}
