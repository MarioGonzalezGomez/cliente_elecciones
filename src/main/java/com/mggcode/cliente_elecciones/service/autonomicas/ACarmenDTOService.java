package com.mggcode.cliente_elecciones.service.autonomicas;

import com.mggcode.cliente_elecciones.DTO.CarmenDTO;
import com.mggcode.cliente_elecciones.DTO.CarmenDtoList;
import com.mggcode.cliente_elecciones.config.Config;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@Service
public class ACarmenDTOService {

    private final Config conf = Config.getConfiguracion();
    private final String ipServer = Config.connectedServer;
    private final String ruta = Config.config.getProperty("rutaFicheros");

    @Autowired
    RestTemplate restTemplate;

    //Este DTO trae los partidos de una circunscripción dada por código
    // ordenados del modo en que Carmen necesita para sus gráficos

    public CarmenDTO findAllOficial(String codAutonomia) {
        ResponseEntity<CarmenDTO> response =
                restTemplate.getForEntity(
                        "http://" + Config.connectedServer + ":8080/autonomicas/carmen/oficial/" + codAutonomia,
                        CarmenDTO.class);
        return response.getBody();
    }

    public File findAllInCsvOficial(String codAutonomia) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/autonomicas/carmen/oficial/" + codAutonomia + "/csv");
        File csv = new File(carpetaBase.getPath() + "F_" + codAutonomia + ".csv");
        FileUtils.copyURLToFile(url, csv);
        return csv;
    }

    public File findAllInExcelOficial(String codAutonomia) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/autonomicas/carmen/oficial/" + codAutonomia + "/excel");
        File excel = new File(carpetaBase.getPath() + File.separator + "F_" + codAutonomia + ".xlsx");
        FileUtils.copyURLToFile(url, excel);
        return excel;
    }

    public File writeCricunscripcionSeleccionadaOficial(String codCirunscripcion) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/autonomicas/carmen/oficial/" + codCirunscripcion + "/csv");
        File csv = new File(carpetaBase.getPath() + File.separator +
                File.separator + "F_autonomicas.csv");
        FileUtils.copyURLToFile(url, csv);
        return csv;
    }

    public File writeAutonomiaSeleccionadaArcoMayoriasOficial(String codAutonomia) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/autonomicas/carmen/oficial/" + codAutonomia + "/csv");
        File csv = new File(carpetaBase.getPath() +
                File.separator + "C_MapaMayorias.csv");
        FileUtils.copyURLToFile(url, csv);
        return csv;
    }

    public CarmenDTO findAllSondeo(String codAutonomia) {
        ResponseEntity<CarmenDTO> response =
                restTemplate.getForEntity(
                        "http://" + Config.connectedServer + ":8080/autonomicas/carmen/sondeo/" + codAutonomia,
                        CarmenDTO.class);
        return response.getBody();
    }

    public File findAllInCsvSondeo(String codAutonomia) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/autonomicas/carmen/sondeo/" + codAutonomia + "/csv");
        File csv = new File(carpetaBase.getPath() + "F_" + codAutonomia + ".csv");
        FileUtils.copyURLToFile(url, csv);
        return csv;
    }

    public File findAllInExcelSondeo(String codAutonomia) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/autonomicas/carmen/sondeo/" + codAutonomia + "/excel");
        File excel = new File(carpetaBase.getPath() + File.separator + "F_" + codAutonomia + ".xlsx");
        FileUtils.copyURLToFile(url, excel);
        return excel;
    }

    public File writeCricunscripcionSeleccionadaSondeo(String codCirunscripcion) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/autonomicas/carmen/sondeo/" + codCirunscripcion + "/csv");
        File csv = new File(carpetaBase.getPath() + File.separator +
                File.separator + "F_autonomicas.csv");
        FileUtils.copyURLToFile(url, csv);
        return csv;
    }

    public File writeAutonomiaSeleccionadaArcoMayoriasSondeo(String codCirunscripcion) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/autonomicas/carmen/sondeo/" + codCirunscripcion + "/csv");
        File csv = new File(carpetaBase.getPath() +
                File.separator + "C_MapaMayoriasSondeo.csv");
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
