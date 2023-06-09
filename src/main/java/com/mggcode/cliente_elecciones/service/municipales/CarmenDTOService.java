package com.mggcode.cliente_elecciones.service.municipales;

import com.mggcode.cliente_elecciones.DTO.CarmenDTO;
import com.mggcode.cliente_elecciones.config.Config;
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

    public CarmenDTO findAllOficial(String codAutonomia, String avance) {
        ResponseEntity<CarmenDTO> response =
                restTemplate.getForEntity(
                        "http://" + Config.connectedServer + ":8080/municipales/carmen/oficial/" + codAutonomia + "/" + avance,
                        CarmenDTO.class);
        return response.getBody();
    }

    public File findAllInCsvOficial(String codAutonomia, String avance) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/municipales/carmen/oficial/" + codAutonomia + "/" + avance + "/csv");
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

    public File findAllInExcelOficial(String codAutonomia, String avance) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/municipales/carmen/oficial/" + codAutonomia + "/" + avance + "/excel");
        File excel = new File(carpetaBase.getPath() + File.separator + "F_" + codAutonomia + ".xlsx");
        FileUtils.copyURLToFile(url, excel);
        return excel;
    }

    public File writeCricunscripcionSeleccionadaOficial(String codCirunscripcion, String avance) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/municipales/carmen/oficial/" + codCirunscripcion + "/" + avance + "/csv");
        File csv;
        if (codCirunscripcion.equals("1800000") || codCirunscripcion.equals("1900000")) {
            csv = new File(carpetaBase.getPath() + File.separator + "F_autonomicas.csv");
        } else {
            csv = new File(carpetaBase.getPath() + File.separator + "F_municipales.csv");
        }
        FileUtils.copyURLToFile(url, csv);
        return csv;
    }

    public File writeAutonomiaSeleccionadaArcoMayoriasOficial(String codAutonomia, String avance) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/municipales/carmen/oficial/" + codAutonomia + "/" + avance + "/csv");
        File csv = new File(carpetaBase.getPath() + File.separator + "C_MapaMayorias.csv");
        FileUtils.copyURLToFile(url, csv);
        return csv;
    }

    public CarmenDTO findAllSondeo(String codAutonomia, String avance) {
        ResponseEntity<CarmenDTO> response =
                restTemplate.getForEntity(
                        "http://" + Config.connectedServer + ":8080/municipales/carmen/sondeo/" + codAutonomia + "/" + avance,
                        CarmenDTO.class);
        return response.getBody();
    }

    public CarmenDTO getSondeoEspecial(String codAutonomia, String avance) {
        ResponseEntity<CarmenDTO> response =
                restTemplate.getForEntity(
                        "http://" + Config.connectedServer + ":8080/municipales/carmen/sondeo/especial/" + codAutonomia + "/" + avance,
                        CarmenDTO.class);
        return response.getBody();
    }

    public File getSondeoEspecialCsv(String codAutonomia, String avance) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/municipales/carmen/sondeo/especial/" + codAutonomia + "/" + avance + "/csv");
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

    public File findAllInCsvSondeo(String codAutonomia, String avance) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/municipales/carmen/sondeo/" + codAutonomia + "/" + avance + "/csv");
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

    public File findAllInExcelSondeo(String codAutonomia, String avance) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/municipales/carmen/sondeo/" + codAutonomia + "/" + avance + "/excel");
        File excel = new File(carpetaBase.getPath() + File.separator + "F_" + codAutonomia + ".xlsx");
        FileUtils.copyURLToFile(url, excel);
        return excel;
    }

    public File writeCricunscripcionSeleccionadaSondeo(String codCirunscripcion, String avance) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/municipales/carmen/sondeo/especial/" + codCirunscripcion + "/" + avance + "/csv");
        File csv;
        if (codCirunscripcion.equals("1800000") || codCirunscripcion.equals("1900000")) {
            csv = new File(carpetaBase.getPath() + File.separator + "F_SondeoAutonomicas.csv");
        } else {
            csv = new File(carpetaBase.getPath() + File.separator + "F_SondeoMunicipales.csv");
        }
        FileUtils.copyURLToFile(url, csv);
        return csv;
    }

    public File writeAutonomiaSeleccionadaArcoMayoriasSondeo(String codCirunscripcion, String avance) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/municipales/carmen/sondeo/especial/" + codCirunscripcion + "/" + avance + "/csv");
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
