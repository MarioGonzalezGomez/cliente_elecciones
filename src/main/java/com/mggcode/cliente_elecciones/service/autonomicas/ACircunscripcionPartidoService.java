package com.mggcode.cliente_elecciones.service.autonomicas;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mggcode.cliente_elecciones.model.CircunscripcionPartido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ACircunscripcionPartidoService {

    @Autowired
    RestTemplate restTemplate;

    public List<CircunscripcionPartido> findAll() throws IOException {
        //restTemplate= new RestTemplate();
        ResponseEntity<CircunscripcionPartido[]> response =
                restTemplate.getForEntity(
                        "http://localhost:8080/autonomicas/cp/mayorias/0200000",
                        CircunscripcionPartido[].class);
        CircunscripcionPartido[] arrayCP = response.getBody();
        return Arrays.asList(arrayCP);
    }

}


