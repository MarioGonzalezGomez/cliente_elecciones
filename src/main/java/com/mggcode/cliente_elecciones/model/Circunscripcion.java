package com.mggcode.cliente_elecciones.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Circunscripcion {

    private String codigo;

    private String codigoComunidad;

    private String codigoProvincia;

    private String codigoMunicipio;

    private String nombreCircunscripcion;

    private double escrutado;

    private int escanios;

    private double avance1;

    private double avance2;

    private double avance3;

    private double participacion;

    private int votantes;

    private int escaniosHistoricos;

    private double avance1Hist;

    private double avance2Hist;

    private double avance3Hist;

    private double participacionHist;

}
