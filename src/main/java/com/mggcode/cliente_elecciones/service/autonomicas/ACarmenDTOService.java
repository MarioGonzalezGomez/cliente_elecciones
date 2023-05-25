package com.mggcode.cliente_elecciones.service.autonomicas;

import com.mggcode.cliente_elecciones.DTO.CarmenDTO;
import com.mggcode.cliente_elecciones.config.Config;
import com.mggcode.cliente_elecciones.model.Circunscripcion;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


@Service
public class ACarmenDTOService {

    private final Config conf = Config.getConfiguracion();
    private final String ipServer = Config.connectedServer;
    private final String ruta = Config.config.getProperty("rutaFicheros");

    @Autowired
    private ACircunscripcionService cirSer;

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

    public void updateAllCsv() {
        File carpetaBase = comprobarCarpetas();
        List<Circunscripcion> list = cirSer.findAutonomias();
        list.forEach(autonomia -> {
            URL url = null;
            try {
                url = new URL("http://" + Config.connectedServer + ":8080/autonomicas/carmen/oficial/" + autonomia.getCodigo() + "/csv");
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            File csv = new File(carpetaBase.getPath() + File.separator + "F_" + autonomia.getCodigo() + ".csv");
            try {
                FileUtils.copyURLToFile(url, csv);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public File findAllInCsvOficial(String codAutonomia) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/autonomicas/carmen/oficial/" + codAutonomia + "/csv");
        File csv = new File(carpetaBase.getPath() + File.separator + "F_" + codAutonomia + ".csv");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Configurar otras propiedades de conexión si es necesario

        try (InputStream inputStream = connection.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(csv)) {
            byte[] buffer = new byte[8192];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

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
        File csv = new File(carpetaBase.getPath() + File.separator + "F_autonomicas.csv");
        FileUtils.copyURLToFile(url, csv);
        return csv;
    }

    public File writeAutonomiaSeleccionadaArcoMayoriasOficial(String codAutonomia) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/autonomicas/carmen/oficial/" + codAutonomia + "/csv");
        File csv = new File(carpetaBase.getPath() + File.separator + "C_MapaMayorias.csv");
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

    public File getSondeoEspecialCsv(String codAutonomia) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/autonomicas/carmen/sondeo/especial/" + codAutonomia + "/csv");
        File csv = new File(carpetaBase.getPath() + File.separator + "F_" + codAutonomia + ".csv");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Configurar otras propiedades de conexión si es necesario

        try (InputStream inputStream = connection.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(csv)) {
            byte[] buffer = new byte[8192];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        return csv;
    }

    public File findAllInCsvSondeo(String codAutonomia) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/autonomicas/carmen/sondeo/" + codAutonomia + "/csv");
        File csv = new File(carpetaBase.getPath() + File.separator + "F_" + codAutonomia + ".csv");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Configurar otras propiedades de conexión si es necesario

        try (InputStream inputStream = connection.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(csv)) {
            byte[] buffer = new byte[8192];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

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
        URL url = new URL("http://" + Config.connectedServer + ":8080/autonomicas/carmen/sondeo/especial/" + codCirunscripcion + "/csv");
        File csv = new File(carpetaBase.getPath() + File.separator + "F_SondeoAutonomicas.csv");
        FileUtils.copyURLToFile(url, csv);
        return csv;
    }

    public File writeAutonomiaSeleccionadaArcoMayoriasSondeo(String codCirunscripcion) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/autonomicas/carmen/sondeo/especial/" + codCirunscripcion + "/csv");
        File csv = new File(carpetaBase.getPath() + File.separator + "C_MapaMayoriasSondeo.csv");
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
