package com.mggcode.cliente_elecciones.conexion;

import java.util.ArrayList;

import static com.mggcode.cliente_elecciones.config.Config.config;

public class ConexionManager {
    private static ConexionManager manager;
    private static ArrayList<ConexionIPF> conexiones;

    public static ConexionManager getConexionManager() {
        if (manager == null) {
            manager = new ConexionManager();
        }
        return manager;
    }

    private ConexionManager() {
        conexiones = new ArrayList<>();
        var nConexiones = Integer.parseInt(config.getProperty("nConexiones"));
        for (int i = 1; i <= nConexiones; i++) {
            if (config.getProperty("direccion" + i).equals("0"))
                conexiones.add(new ConexionIPF("localhost"));
            else
                conexiones.add(new ConexionIPF(config.getProperty("direccion" + i)));
        }

    }

    public ConexionIPF getConexionByAdress(String address) {
        return conexiones.stream().filter(conexionIPF -> conexionIPF.getDireccion().equals(address)).toList().get(0);
    }
}
