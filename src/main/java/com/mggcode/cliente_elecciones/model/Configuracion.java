package com.mggcode.cliente_elecciones.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Configuracion {
    //IPF
    private int nConexiones;

    private String direccion1;
    private String direccion2;
    private String direccion3;
    private String direccion4;

    private String puerto;

    @JsonProperty("BDAutonomicas")
    private String bdAutonomicas;
    @JsonProperty("BDMunicipales")
    private String bdMunicipales;

    //Servidor
    private String ipServer;
    private String ipServerReserva;
    private String ipServerLocal;

    //
    private String rutaFicheros;
}
