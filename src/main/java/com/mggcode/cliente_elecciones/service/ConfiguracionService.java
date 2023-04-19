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
    private final String rutaConfig = "src/main/resources/config.properties";


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
        configuracion.setNConexiones(Integer.parseInt(propiedades.getProperty("nConexiones")));

        //TODO:Manejar esto como una lista si vemos que hay casos de más conexiones
        configuracion.setDireccion1(propiedades.getProperty("direccion1"));
        configuracion.setDireccion2(propiedades.getProperty("direccion2"));
        configuracion.setDireccion3(propiedades.getProperty("direccion3"));
        configuracion.setDireccion4(propiedades.getProperty("direccion4"));

        configuracion.setPuerto(propiedades.getProperty("puerto"));

        configuracion.setBdAutonomicas(propiedades.getProperty("BDAutonomicas"));
        configuracion.setBdMunicipales(propiedades.getProperty("BDMunicipales"));

        configuracion.setIpServer(propiedades.getProperty("ipServer"));
        configuracion.setIpServerReserva(propiedades.getProperty("ipServerReserva"));
        configuracion.setIpServerLocal(propiedades.getProperty("ipServerLocal"));

        configuracion.setRutaFicheros(propiedades.getProperty("rutaFicheros"));
        return configuracion;
    }


    public void guardarConfiguracion(Configuracion configuracion) {
        try {
            Properties propiedades = new Properties();
            propiedades.setProperty("nConexiones", String.valueOf(configuracion.getNConexiones()));

            propiedades.setProperty("direccion1", configuracion.getDireccion1());
            propiedades.setProperty("direccion2", configuracion.getDireccion2());
            propiedades.setProperty("direccion3", configuracion.getDireccion3());
            propiedades.setProperty("direccion4", configuracion.getDireccion4());

            propiedades.setProperty("puerto", configuracion.getPuerto());

            propiedades.setProperty("BDAutonomicas", configuracion.getBdAutonomicas());
            propiedades.setProperty("BDMunicipales", configuracion.getBdMunicipales());

            propiedades.setProperty("ipServer", configuracion.getIpServer());
            propiedades.setProperty("ipServerReserva", configuracion.getIpServerReserva());
            propiedades.setProperty("ipServerLocal", configuracion.getIpServerLocal());

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
