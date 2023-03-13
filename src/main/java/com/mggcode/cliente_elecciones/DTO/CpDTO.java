package com.mggcode.cliente_elecciones.DTO;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CpDTO {
    private String codigoPartido;
    private String codigoPadre;
    private int escanos_desde;
    private int escanos_hasta;
    private int escanos_desde_hist;
    private int escanos_hasta_hist;
    private double porcentajeVoto;
    private double porcentajeVotoHistorico;
    private int numVotantes;
    private String siglas;
    private String literalPartido;

}
