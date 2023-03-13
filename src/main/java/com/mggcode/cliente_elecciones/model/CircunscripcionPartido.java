package com.mggcode.cliente_elecciones.model;

import lombok.Data;

@Data
public class CircunscripcionPartido {

    private Key key;

    private int escanos_desde;

    private int escanos_hasta;

    private double porcentajeVoto;

    private int numVotantes;

    private int escanos_desde_hist;

    private int escanos_hasta_hist;

    private double votantesHistorico;

    private int numVotantesHistorico;

    private int escanos_desde_sondeo;

    private int escanos_hasta_sondeo;

    private double porcentajeVotoSondeo;

}
