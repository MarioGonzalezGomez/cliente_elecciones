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

    private double aperturaOficial = 0;
    private double aperturaDesdeSondeo = 0;
    private double aperturaHastaSondeo = 0;


    public static LogicaArcos getInstance() {
        if (instance == null)
            instance = new LogicaArcos();
        return instance;
    }


    //Manejaremos 4 tipos de arco: 1 = oficial ; 2=principal(Similar a Hasta sondeo); 3 = hasta; 4 = desde
    public String getApertura(List<CircunscripcionPartido> cps, CircunscripcionPartido seleccionado, int tipoArco) {
        iniciarListas();
        DecimalFormat df = getFormat();
        getSumatorios(cps);
        getAperturasArco(seleccionado);
        String apertura = "";
        if (tipoArco == 1) {
            apertura = df.format(aperturaOficial);
        }
        if (tipoArco == 2) {
            apertura = df.format(aperturaHastaSondeo);
        }
        if (tipoArco == 3) {
            apertura = df.format(aperturaDesdeSondeo);
        }
        if (tipoArco == 4) {
            apertura = df.format(aperturaHastaSondeo);
        }
        return apertura;

    }

    private void iniciarListas() {
        posicionesIniciales = new ArrayList<>();
        posicionesIniciales.add(0.0);
        posicionesIniciales.add(0.0);
        posicionesIniciales.add(0.0);
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
        //System.out.println("ESCANOS HASTA: " + cp.getEscanos_hasta());
        //System.out.println("ESCANOS DESDE SONDEO: " + cp.getEscanos_desde_sondeo());
        //System.out.println("ESCANOS HASTA SONDE: " + cp.getEscanos_hasta_sondeo());
        //System.out.println("ESCANOS HASTA: " + cp.getEscanos_hasta());


        //calcular aperturas maneniendo el 0 si se va a dividir entre 0
        if (totalHastaOficial != 0)
            aperturaOficial = (cp.getEscanos_hasta() * gradosTotales) / totalHastaOficial;

        if (totalDesdeSondeo != 0)
            aperturaDesdeSondeo = (cp.getEscanos_desde_sondeo() * gradosTotales) / totalHastaSondeo;

        if (totalHastaSondeo != 0)
            aperturaHastaSondeo = (cp.getEscanos_hasta_sondeo() * gradosTotales) / totalHastaSondeo;

        aperturas.add(aperturaOficial);
        aperturas.add(aperturaDesdeSondeo);
        aperturas.add(aperturaHastaSondeo);
        return aperturas;
    }


}
