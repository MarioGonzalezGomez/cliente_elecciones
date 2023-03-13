package com.mggcode.cliente_elecciones;

import com.mggcode.cliente_elecciones.service.autonomicas.ACircunscripcionPartidoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@SpringBootApplication
public class ClienteEleccionesApplication {

    @Bean
    public RestTemplate getresttemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(ClienteEleccionesApplication.class, args);
    }

}
