package com.mggcode.cliente_elecciones.data;

@lombok.Data
public class Data {
    private static Data instance = null;
    public  String autonomiaSeleccionada;
    public  String circunscripcionSeleccionada;

    private Data() {
        autonomiaSeleccionada = "";
        circunscripcionSeleccionada = "";
    }

    public static Data getInstance() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }


}
