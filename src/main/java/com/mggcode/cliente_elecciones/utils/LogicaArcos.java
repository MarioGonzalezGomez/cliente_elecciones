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
    private ArrayList<Double> sumatorios;

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
            apertura = df.format(aperturas.get(0));
        }
        if (tipoArco == 2) {
            apertura = df.format(aperturas.get(0));
        }
        if (tipoArco == 3) {
            apertura = df.format(aperturas.get(0));
        }
        if (tipoArco == 4) {
            apertura = df.format(aperturas.get(0));
        }
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

    private ArrayList<Double> getSumatorios(List<CircunscripcionPartido> cps) {
        sumatorios = new ArrayList<>();

        double sumatorioHasta = cps.stream().mapToInt(CircunscripcionPartido::getEscanos_hasta).sum();
        double sumatorioHastaSondeo = cps.stream().mapToInt(CircunscripcionPartido::getEscanos_hasta_sondeo).sum();

        sumatorios.add(sumatorioHasta);
        //Se añade una segunda vez, ya que la apertura de los "Desde" tomará también como total el sumatorio de los hasta
        sumatorios.add(sumatorioHastaSondeo);
        //sumatorios.add(sumatorioDesdeSondeo);
        sumatorios.add(sumatorioHastaSondeo);

        return sumatorios;
    }

    private ArrayList<Double> getAperturasArco(CircunscripcionPartido cp) {
        ArrayList<Double> aperturas = new ArrayList<>();
        double aperturaOficial = cp.getEscanos_hasta() * gradosTotales / sumatorios.get(0);
        posicionesFinales.set(0, posicionesIniciales.get(0) + aperturaOficial);
        double aperturaDesdeSondeo = cp.getEscanos_desde_sondeo() * gradosTotales / sumatorios.get(1);
        posicionesFinales.set(1, posicionesIniciales.get(1) + aperturaDesdeSondeo);
        double aperturaHastaSondeo = cp.getEscanos_hasta_sondeo() * gradosTotales / sumatorios.get(2);
        posicionesFinales.set(2, posicionesIniciales.get(2) + aperturaHastaSondeo);
        aperturas.add(aperturaOficial);
        aperturas.add(aperturaDesdeSondeo);
        aperturas.add(aperturaHastaSondeo);
        return aperturas;
    }
}
