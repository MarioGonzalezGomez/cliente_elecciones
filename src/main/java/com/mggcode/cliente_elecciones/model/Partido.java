package com.mggcode.cliente_elecciones.model;

import lombok.Data;

@Data
public class Partido {

    private String codigo;

    private String siglas;

    private String codigoPadre;

    private String nombreCompleto;
}
