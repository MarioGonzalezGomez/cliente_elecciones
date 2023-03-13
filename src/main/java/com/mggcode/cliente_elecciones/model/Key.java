package com.mggcode.cliente_elecciones.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Key implements Serializable {


    private String circunscripcion;
    private String partido;

    public Key() {
    }

    public Key(String codCircunscripcion, String codPartido) {
        this.circunscripcion = codCircunscripcion;
        this.partido = codPartido;
    }
}
