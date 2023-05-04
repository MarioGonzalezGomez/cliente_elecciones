package com.mggcode.cliente_elecciones.service.municipales;

import com.mggcode.cliente_elecciones.config.Config;
import com.mggcode.cliente_elecciones.model.Literal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class LiteralService {

    private final Config conf = Config.getConfiguracion();
    private final String ipServer = Config.connectedServer;
    //private final String ruta = Config.config.getProperty("rutaFicheros") + "\\municipales";

    @Autowired
    RestTemplate restTemplate;

    public List<Literal> findAll() {
        ResponseEntity<Literal[]> response =
                restTemplate.getForEntity(
                        "http://" + Config.connectedServer + ":8080/municipales/literales",
                        Literal[].class);
        Literal[] arrayP = response.getBody();
        return Arrays.asList(arrayP);
    }

    public Literal findById(String id) {
        ResponseEntity<Literal> response =
                restTemplate.getForEntity(
                        "http://" + Config.connectedServer + ":8080/municipales/literales/" + id,
                        Literal.class);
        return response.getBody();
    }

    // private File comprobarCarpetas() {
    //     File municipales = new File(ruta);
    //     if (!municipales.exists()) {
    //         municipales.mkdir();
    //     }
    //     File literales = new File(ruta + "\\LITERALES");
    //     if (!literales.exists()) {
    //         literales.mkdir();
    //     }
    //     File csv = new File(literales.getPath() + "\\CSV");
    //     File excel = new File(literales.getPath() + "\\EXCEL");
    //     if (!csv.exists()) {
    //         csv.mkdir();
    //         excel.mkdir();
    //     }
    //     return literales;
    // }
}
