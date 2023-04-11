package com.mggcode.cliente_elecciones.model;

import lombok.Data;

@Data
public class Configuracion {
    private String BDAutonomicas;
    private String BDMunicipales;
    private String ipServer;
    private String rutaFicheros;
}
