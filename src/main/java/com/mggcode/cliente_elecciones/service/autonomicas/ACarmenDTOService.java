package com.mggcode.cliente_elecciones.service.autonomicas;

import com.mggcode.cliente_elecciones.DTO.CarmenDTO;
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
public class ACarmenDTOService {

    private final Config conf = Config.getConfiguracion();
    private final String ruta = Config.config.getProperty("rutaFicheros") + "\\Autonomicas";

    @Autowired
    RestTemplate restTemplate;

    //Este DTO trae los partidos de una circunscripción dada por código
    // ordenados del modo en que Carmen necesita para sus gráficos
    public CarmenDTO findAll(String codAutonomia) {
        String ipServer = Config.config.getProperty("ipServer");
        ResponseEntity<CarmenDTO> response =
                restTemplate.getForEntity(
                        "http://" + ipServer + ":8080/autonomicas/carmen/" + codAutonomia,
                        CarmenDTO.class);
        return response.getBody();
    }

    public void findAllInCsv(String codAutonomia) throws IOException {
        String ipServer = Config.config.getProperty("ipServer");
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + ipServer + ":8080/autonomicas/carmen/" + codAutonomia + "/csv");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\CSV\\CarmenDTO_" + codAutonomia + ".csv"));
    }

    public void findAllInExcel(String codAutonomia) throws IOException {
        String ipServer = Config.config.getProperty("ipServer");
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + ipServer + ":8080/autonomicas/carmen/" + codAutonomia + "/excel");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\EXCEL\\CarmenDTO_" + codAutonomia + ".xlsx"));
    }

    private File comprobarCarpetas() {
        File autonomicas = new File(ruta);
        if (!autonomicas.exists()) {
            autonomicas.mkdir();
        }
        File partidos = new File(ruta + "\\CARMEN");
        if (!partidos.exists()) {
            partidos.mkdir();
        }
        File csv = new File(partidos.getPath() + "\\CSV");
        File excel = new File(partidos.getPath() + "\\EXCEL");
        if (!csv.exists()) {
            csv.mkdir();
            excel.mkdir();
        }
        return partidos;
    }
}
