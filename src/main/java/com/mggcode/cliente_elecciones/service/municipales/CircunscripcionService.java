package com.mggcode.cliente_elecciones.service.municipales;

import com.mggcode.cliente_elecciones.config.Config;
import com.mggcode.cliente_elecciones.exception.ConnectionException;
import com.mggcode.cliente_elecciones.model.Circunscripcion;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class CircunscripcionService {
    private final Config conf = Config.getConfiguracion();
    private final String ruta = Config.config.getProperty("rutaFicheros") + "\\Municipales";

    @Autowired
    RestTemplate restTemplate;

    public List<Circunscripcion> findAll() throws ConnectionException {
        if (!Config.checkConnection(Config.connectedServer)) {
            throw new ConnectionException();
        }
        ResponseEntity<Circunscripcion[]> response =
                restTemplate.getForEntity(
                        "http://" + Config.connectedServer + ":8080/municipales/circunscripciones",
                        Circunscripcion[].class);

        Circunscripcion[] arrayP = response.getBody();
        return Arrays.asList(arrayP);
    }

    public List<Circunscripcion> filtradasPorMostrar() throws ConnectionException {
        if (!Config.checkConnection(Config.connectedServer)) {
            throw new ConnectionException();
        }
        ResponseEntity<Circunscripcion[]> response =
                restTemplate.getForEntity(
                        "http://" + Config.connectedServer + ":8080/municipales/circunscripciones/filtrada",
                        Circunscripcion[].class);
        Circunscripcion[] arrayP = response.getBody();
        return Arrays.asList(arrayP);
    }

    public void findAllInCsv() throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/municipales/circunscripciones/csv");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\CSV\\circunscripciones.csv"));
    }

    public void findAllInExcel() throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/municipales/circunscripciones/excel");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\EXCEL\\circunscripciones.xlsx"));
    }

    public Circunscripcion findById(String id) {
        ResponseEntity<Circunscripcion> response =
                restTemplate.getForEntity(
                        "http://" + Config.connectedServer + ":8080/municipales/circunscripciones/" + id,
                        Circunscripcion.class);
        return response.getBody();
    }

    public void findByIdInCsv(String id) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/municipales/circunscripciones/" + id + "/csv");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\CSV\\circunscripcion_" + id + ".csv"));
    }

    public void findByIdInExcel(String id) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/municipales/circunscripciones/" + id + "/excel");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\EXCEL\\circunscripcion_" + id + ".xlsx"));
    }


    private File comprobarCarpetas() {
        File municipales = new File(ruta);
        if (!municipales.exists()) {
            municipales.mkdir();
        }
        File circunscripciones = new File(ruta + "\\CIRCUNSCRIPCIONES");
        if (!circunscripciones.exists()) {
            circunscripciones.mkdir();
        }
        File csv = new File(circunscripciones.getPath() + "\\CSV");
        File excel = new File(circunscripciones.getPath() + "\\EXCEL");
        if (!csv.exists()) {
            csv.mkdir();
            excel.mkdir();
        }
        return circunscripciones;
    }

    public List<Circunscripcion> findByAutonomia(String codAuto) {
        ResponseEntity<Circunscripcion[]> response =
                restTemplate.getForEntity(
                        "http://" + Config.connectedServer + ":8080/municipales/circunscripciones",
                        Circunscripcion[].class);
        var cod = codAuto.substring(0, 2);
        var res = Arrays.stream(Objects.requireNonNull(response.getBody()))
                .filter(circunscripcion -> Objects.equals(circunscripcion.getCodigoComunidad(), codAuto.substring(0, 2)))
                .filter(circunscripcion -> !circunscripcion.getCodigo().endsWith("00000"))
                .filter(circunscripcion -> !circunscripcion.getCodigo().startsWith("99"))
                .toList();

        return res;
    }
}
