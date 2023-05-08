package com.mggcode.cliente_elecciones.service.municipales;

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
public class CarmenDTOService {

    private final Config conf = Config.getConfiguracion();
    private final String ipServer = Config.connectedServer;
    private final String ruta = Config.config.getProperty("rutaFicheros");

    @Autowired
    RestTemplate restTemplate;

    //Este DTO trae los partidos de una circunscripción dada por código
    // ordenados del modo en que Carmen necesita para sus gráficos
    public CarmenDTO findByCodigoOficial(String codAutonomia) {
        ResponseEntity<CarmenDTO> response =
                restTemplate.getForEntity(
                        "http://" + Config.connectedServer + ":8080/municipales/carmen/oficial/" + codAutonomia,
                        CarmenDTO.class);
        return response.getBody();
    }

    public CarmenDTO findByCodigoSondeo(String codigo) {
        ResponseEntity<CarmenDTO> response =
                restTemplate.getForEntity(
                        "http://" + Config.connectedServer + ":8080/municipales/carmen/sondeo/" + codigo,
                        CarmenDTO.class);
        return response.getBody();
    }

    public File findAllInCsv(String codAutonomia) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/municipales/carmen/" + codAutonomia + "/csv");
        File csv = new File(carpetaBase.getPath() + "F_" + codAutonomia + ".csv");
        FileUtils.copyURLToFile(url, csv);
        return csv;
    }

    public File findAllInExcel(String codAutonomia) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/municipales/carmen/" + codAutonomia + "/excel");
        File excel = new File(carpetaBase.getPath() + File.separator + "F_" + codAutonomia + ".xlsx");
        FileUtils.copyURLToFile(url, excel);
        return excel;
    }

    public File writeCricunscripcionSeleccionada(String codCirunscripcion) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/municipales/carmen/" + codCirunscripcion + "/csv");
        File csv = new File(carpetaBase.getPath() + File.separator +
                File.separator + "F_Municipales.csv");
        FileUtils.copyURLToFile(url, csv);
        return csv;
    }

    public File writeAutonomiaSeleccionadaArcoMayorias(String codAutonomia) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/autonomicas/carmen/" + codAutonomia + "/csv");
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
