package com.mggcode.cliente_elecciones.service.municipales;

import com.mggcode.cliente_elecciones.DTO.ResultadosDTO;
import com.mggcode.cliente_elecciones.config.Config;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@Service
public class ResultadosDTOService {

    private final Config conf = Config.getConfiguracion();
    private final String ipServer = Config.connectedServer;
    private final String ruta = Config.config.getProperty("rutaFicheros");

    @Autowired
    RestTemplate restTemplate;

    public ResultadosDTO findById(String circunscripcion) {
        ResponseEntity<ResultadosDTO> response =
                restTemplate.getForEntity(
                        "http://" + Config.connectedServer + ":8080/municipales/resultados/" + circunscripcion,
                        ResultadosDTO.class);
        return response.getBody();
    }

    public File findByIdCsv(String circunscripcion) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/municipales/resultados/" + circunscripcion + "/csv");
        File csv = new File(carpetaBase.getPath() +
                File.separator + "MapaComunidad.csv");
        FileUtils.copyURLToFile(url, csv);
        return csv;
    }

    private File comprobarCarpetas() {
        File datos = new File(ruta);
        if (!datos.exists()) {
            datos.mkdir();
        }
        return datos;
    }
}
