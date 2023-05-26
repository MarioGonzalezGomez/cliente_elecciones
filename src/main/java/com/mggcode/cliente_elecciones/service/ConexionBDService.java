package com.mggcode.cliente_elecciones.service;

import com.mggcode.cliente_elecciones.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ConexionBDService {

    private final Config conf = Config.getConfiguracion();
    private final String ipServer = Config.connectedServer;
    @Autowired
    RestTemplate restTemplate;

    public void conectPrincipalBD() {
        restTemplate.getForEntity(
                "http://" + Config.connectedServer + ":8080/principal",
                String.class);
    }

    public void conectReservaBD() {
        restTemplate.getForEntity(
                "http://" + Config.connectedServer + ":8080/reserva",
                String.class);
    }

    public void conectLocalBD() {
        restTemplate.getForEntity(
                "http://" + Config.connectedServer + ":8080/local",
                String.class);
    }

}
