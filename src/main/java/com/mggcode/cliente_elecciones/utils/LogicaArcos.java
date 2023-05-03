package com.mggcode.cliente_elecciones.utils;

import com.mggcode.cliente_elecciones.model.CircunscripcionPartido;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class LogicaArcos {
    private static LogicaArcos instance = null;
    private final int gradosTotales = 180;
    private ArrayList<Double> posicionesIniciales;
    private ArrayList<Double> posicionesFinales;


    private double aperturaOficial = 0;
    private double aperturaDesdeSondeo = 0;
    private double aperturaHastaSondeo = 0;


    public static LogicaArcos getInstance() {
        if (instance == null)
            instance = new LogicaArcos();
        return instance;
    }


    //Manejaremos 3 tipos de arco: 1 = oficial ; 2=desde; 3 = hasta
    public String getApertura(List<CircunscripcionPartido> cps, CircunscripcionPartido seleccionado, int tipoArco) {
        iniciarListas();
        DecimalFormat df = getFormat();
        getSumatorios(cps);
        List<Double> aperturas = getAperturasArco(seleccionado);
        String apertura = "";
        if (tipoArco == 1) {
            apertura = df.format(aperturaOficial);
            //  System.out.println("1: " + apertura);

        }
        if (tipoArco == 2) {
            apertura = df.format(aperturaHastaSondeo);
            // System.out.println("2: " + apertura);

        }
        if (tipoArco == 3) {
            apertura = df.format(aperturaDesdeSondeo);
            //  System.out.println("3: " + apertura);
        }
        if (tipoArco == 4) {
            apertura = df.format(aperturaHastaSondeo);
            //  System.out.println("4: " + apertura);

        }
        //System.out.println("---" + apertura);
        return apertura;

    }

    private void iniciarListas() {
        posicionesIniciales = new ArrayList<>();
        posicionesIniciales.add(0.0);
        posicionesIniciales.add(0.0);
        posicionesIniciales.add(0.0);
        posicionesFinales = new ArrayList<>();
        posicionesFinales.add(0.0);
        posicionesFinales.add(0.0);
        posicionesFinales.add(0.0);
    }

    public DecimalFormat getFormat() {
        DecimalFormat df = new DecimalFormat("#.##");
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(dfs);
        return df;
    }

    Double totalHastaOficial = 0.0;
    Double totalHastaSondeo = 0.0;
    Double totalDesdeSondeo = 0.0;
    ArrayList<Double> sumatorios = new ArrayList<>();

    private ArrayList<Double> getSumatorios(List<CircunscripcionPartido> cps) {

        sumatorios.add(getTotalOficial(cps));

        sumatorios.add(getTotalHastaSondeo(cps));
        sumatorios.add(getTotalHastaSondeo(cps));
        totalHastaOficial = getTotalOficial(cps);
        totalDesdeSondeo = getTotalDesdeSondeo(cps);
        totalHastaSondeo = getTotalHastaSondeo(cps);
        return sumatorios;
    }

    private double getTotalOficial(List<CircunscripcionPartido> partidos) {
        return partidos.stream().mapToInt(CircunscripcionPartido::getEscanos_hasta).sum();
    }

    private double getTotalHastaSondeo(List<CircunscripcionPartido> partidos) {
        return partidos.stream().mapToInt(CircunscripcionPartido::getEscanos_hasta_sondeo).sum();
    }

    private double getTotalDesdeSondeo(List<CircunscripcionPartido> partidos) {
        return partidos.stream().mapToInt(CircunscripcionPartido::getEscanos_desde_sondeo).sum();
    }


    private ArrayList<Double> getAperturasArco(CircunscripcionPartido cp) {
        ArrayList<Double> aperturas = new ArrayList<>();

        if (totalHastaOficial != 0)
            aperturaOficial = (cp.getEscanos_hasta() * gradosTotales) / totalHastaOficial;

        if (totalDesdeSondeo != 0)
            aperturaDesdeSondeo = (cp.getEscanos_desde_sondeo() * gradosTotales) / totalDesdeSondeo;

        if (totalHastaSondeo != 0)
            aperturaHastaSondeo = (cp.getEscanos_hasta_sondeo() * gradosTotales) / totalHastaSondeo;


        posicionesFinales.set(0, posicionesIniciales.get(0) + aperturaOficial);
        posicionesFinales.set(2, posicionesIniciales.get(2) + aperturaHastaSondeo);
        posicionesFinales.set(1, posicionesIniciales.get(1) + aperturaDesdeSondeo);

        aperturas.add(aperturaOficial);
        aperturas.add(aperturaDesdeSondeo);
        aperturas.add(aperturaHastaSondeo);
        return aperturas;
    }


}
