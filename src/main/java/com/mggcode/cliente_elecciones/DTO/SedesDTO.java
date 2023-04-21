package com.mggcode.cliente_elecciones.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SedesDTO {
    private String codigoCircunscripcion;
    private String codigoPartido;
    private String codigoPadre;
    private int escanos_desde;
    private int escanos_hasta;
    private int escanos_hist;
    private double porcentajeVoto;
    private double porcentajeVotoHistorico;
    private int numVotantes;
    private String siglas;
    private String literalPartido;
    private int numVotantes_hist;
}
