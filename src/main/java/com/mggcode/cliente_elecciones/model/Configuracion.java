package com.mggcode.cliente_elecciones.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Configuracion {
    //IPF
    @JsonProperty("nConexiones")
    private int nConexiones;

    private String direccion1;
    private String direccion2;
    private String direccion3;
    private String direccion4;

    private String puerto;
    @JsonProperty("BDFaldones")
    private String bdFaldones;
    @JsonProperty("BDCartones")
    private String bdCartones;

    //Servidor
    private String ipServer;
    private String ipServerReserva;

    //
    private String rutaFicheros;
}
