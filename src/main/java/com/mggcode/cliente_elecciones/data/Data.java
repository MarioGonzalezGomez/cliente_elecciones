package com.mggcode.cliente_elecciones.data;

@lombok.Data
public class Data {
    private static Data instance = null;
    public  String autonomiaSeleccionada = "0000000";
    public  String circunscripcionSeleccionada = "0000000";

    private Data() {
        autonomiaSeleccionada = "0000000";
        circunscripcionSeleccionada = "0000000";
    }

    public static Data getInstance() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }


}
