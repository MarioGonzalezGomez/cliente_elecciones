package com.mggcode.cliente_elecciones.service.municipales;

import com.mggcode.cliente_elecciones.DTO.SedesDTO;
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
public class SedesDTOService {

    private final Config conf = Config.getConfiguracion();
    private final String ipServer = Config.connectedServer;
    private final String ruta = Config.config.getProperty("rutaFicheros");

    @Autowired
    RestTemplate restTemplate;

    public SedesDTO findById(String circunscripcion, String partido) {
        ResponseEntity<SedesDTO> response =
                restTemplate.getForEntity(
                        "http://" + Config.connectedServer + ":8080/municipales/sedes/" + circunscripcion + "/" + partido,
                        SedesDTO.class);
        return response.getBody();
    }

    public File findByIdCsv(String circunscripcion, String partido) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/municipales/sedes/" + circunscripcion + "/" + partido + "/csv");

        File csv = new File(carpetaBase.getPath() +
                File.separator + "F_sedes.csv");
        FileUtils.copyURLToFile(url, csv);
        return csv;
    }

    public File findByIdExcel(String circunscripcion, String partido) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/municipales/sedes/" + circunscripcion + "/" + partido + "/excel");
        File excel = new File(carpetaBase.getPath() +
                File.separator + "F_sedes.xlsx");
        FileUtils.copyURLToFile(url, excel);
        return excel;
    }
    private File comprobarCarpetas() {
        File datos = new File(ruta);
        if (!datos.exists()) {
            datos.mkdir();
        }
        return datos;
    }

}

