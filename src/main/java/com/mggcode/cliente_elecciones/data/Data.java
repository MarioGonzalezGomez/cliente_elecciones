package com.mggcode.cliente_elecciones.data;

@lombok.Data
public class Data {
    private static Data instance = null;
    public String autonomiaSeleccionada;
    public String circunscripcionSeleccionada;
    public String partidoSeleccionado;

    private Data() {
        autonomiaSeleccionada = "";
        circunscripcionSeleccionada = "";
        partidoSeleccionado = "";
    }

    public static Data getInstance() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }


}
