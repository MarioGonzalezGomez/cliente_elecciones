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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Key key = (Key) o;

        if (!circunscripcion.equals(key.circunscripcion)) return false;
        return partido.equals(key.partido);
    }

    @Override
    public int hashCode() {
        int result = circunscripcion.hashCode();
        result = 31 * result + partido.hashCode();
        return result;
    }
}
