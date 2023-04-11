package com.mggcode.cliente_elecciones.service;

import com.mggcode.cliente_elecciones.model.Configuracion;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

@Service
public class ConfiguracionService {
    private String rutaConfig = "src/main/resources/config.properties";


    public Configuracion cargarConfiguracion() {
        Properties propiedades = new Properties();
        try {
            FileInputStream archivoEntrada = new FileInputStream(rutaConfig);
            propiedades.load(archivoEntrada);
            archivoEntrada.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Configuracion configuracion = new Configuracion();
        configuracion.setBDAutonomicas(propiedades.getProperty("BDAutonomicas"));
        configuracion.setBDMunicipales(propiedades.getProperty("BDMunicipales"));
        configuracion.setIpServer(propiedades.getProperty("ipServer"));
        configuracion.setRutaFicheros(propiedades.getProperty("rutaFicheros"));
        return configuracion;
    }


    public void guardarConfiguracion(Configuracion configuracion) {
        try {
            Properties propiedades = new Properties();
            propiedades.setProperty("BDAutonomicas", configuracion.getBDAutonomicas());
            propiedades.setProperty("BDMunicipales", configuracion.getBDMunicipales());
            propiedades.setProperty("ipServer", configuracion.getIpServer());
            propiedades.setProperty("rutaFicheros", configuracion.getRutaFicheros());

            File archivoPropiedades = new File(rutaConfig);
            FileOutputStream archivoSalida = new FileOutputStream(archivoPropiedades);
            propiedades.store(archivoSalida, "Archivo de configuración");
            archivoSalida.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
