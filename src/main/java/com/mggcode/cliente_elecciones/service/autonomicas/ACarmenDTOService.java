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

    public List<CarmenDTO> findAll() {
        ResponseEntity<CarmenDtoList> response = restTemplate.getForEntity("http://" + Config.connectedServer + ":8080/autonomicas/carmen",
                CarmenDtoList.class);
        return response.getBody().getCarmenDTOList();
    }

    public CarmenDTO findById(String codAutonomia) {
        ResponseEntity<CarmenDTO> response =
                restTemplate.getForEntity(
                        "http://" + Config.connectedServer + ":8080/autonomicas/carmen/" + codAutonomia,
                        CarmenDTO.class);
        return response.getBody();
    }

    //Escribe todos los csv de las autonomias
    public void findAllCsv() {
        File carpetaBase = comprobarCarpetas();
        ArrayList<File> csvList = new ArrayList<>();
        List<CarmenDTO> carmenDTOList = findAll();
        carmenDTOList.forEach(carmenDTO -> {
            try {
                File result = findByIdCsv(carmenDTO.getCircunscripcion().getCodigo());
                csvList.add(result);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    //Escribe un csv de una autonomía
    public File findByIdCsv(String codAutonomia) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/autonomicas/carmen/" + codAutonomia + "/csv");

        File csv = new File(carpetaBase.getPath() +
                File.separator + "F_" + codAutonomia + ".csv");
        FileUtils.copyURLToFile(url, csv);
        return csv;
    }


    //Sobreescribe en el archivo F_Autonomicas con la autonomía seleccionada
    public File writeAutonomiaSeleccionada(String codAutonomia) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/autonomicas/carmen/" + codAutonomia + "/csv");
        File csv = new File(carpetaBase.getPath() +
                File.separator + "F_Autonomicas.csv");
        FileUtils.copyURLToFile(url, csv);
        return csv;
    }

    public File writeAutonomiaSeleccionadaArcoMayorias(String codAutonomia) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/autonomicas/carmen/" + codAutonomia + "/csv");
        File csv = new File(carpetaBase.getPath() +
                File.separator + "C_MapaMayorias.csv");
        FileUtils.copyURLToFile(url, csv);
        return csv;
    }

    public File findByIdExcel(String codAutonomia) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/autonomicas/carmen/" + codAutonomia + "/excel");
        File excel = new File(carpetaBase.getPath() + File.separator + "F_" + codAutonomia + ".xlsx");
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
