package com.mggcode.cliente_elecciones.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CircunscripcionDTO {

    private String codigo;
    private String codigoComunidad;
    private String codigoProvincia;
    private String codigoMunicipio;
    private String nombreCircunscripcion;
    private double escrutado;
    private int escanios;
    private double participacion;
    private double participacionHistorico;
    private double participacionMedia;
    private String literalParticipacion;
    private int votantes;
    private int escaniosHistoricos;
    private String anioUltimosDatos;
    private double avance2Hist;
    private double avance3Hist;
    private double participacionHist;
}