package com.mggcode.cliente_elecciones.config;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigIPF {

    private static ConfigIPF configuracion;
    public static Properties config;

    private ConfigIPF() {
        config = new Properties();
        loadConfig();
    }

    public static ConfigIPF getConfiguracion() {
        if (configuracion == null) {
            configuracion = new ConfigIPF();
        }
        return configuracion;
    }

    public void loadConfig() {
        try {
            config.load(new FileInputStream("src/main/resources/config.properties"));
        } catch (Exception e) {
            System.out.println("Error cargando configuración");
        }
    }
}
