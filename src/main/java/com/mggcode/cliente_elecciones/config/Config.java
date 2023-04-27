package com.mggcode.cliente_elecciones.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Config {

    private static Config configuracion;
    public static Properties config;
    public static String connectedServer;

    private Config() {
        config = new Properties();
        loadConfig();
    }

    public static Config getConfiguracion() {
        if (configuracion == null) {
            configuracion = new Config();
        }
        return configuracion;
    }

    public void loadConfig() {
        try {
            config.load(new FileInputStream("src/main/resources/config.properties"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error cargando configuración");
        }
    }

    public static String getIpServer() {
        return config.getProperty("ipServer");
    }

    public static String getIpReserva() {
        return config.getProperty("ipServerReserva");
    }

    public static String getIpLocal() {
        return config.getProperty("ipServerLocal");
    }

    public static boolean checkConnection(String url) {
        HttpURLConnection connection = null;
        try {
            URL urlObj = new URL("http://" + url + ":8080");
            //System.out.println("Conectando a: " + urlObj);
            connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(5000);
            int responseCode = connection.getResponseCode();
            return (responseCode == HttpURLConnection.HTTP_OK);
        } catch (IOException e) {
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static List<String> getServers() {
        List<String> servers = new ArrayList<>();
        servers.add(getIpServer());
        servers.add(getIpLocal());
        servers.add(getIpReserva());
        return servers;
    }

    public static boolean checkConnectionWithRetry() {
        List<String> addresses = getServers();
        boolean isConnected = false;
        int serverIndex = 0;
        String server = "";
        while (!isConnected) {
            String url = addresses.get(serverIndex);
            isConnected = checkConnection(url);
            if (isConnected) server = addresses.get(serverIndex);
            System.out.println("Probando: " + url);
            serverIndex = (serverIndex + 1) % addresses.size();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        connectedServer = server;
        return isConnected;
    }
}
