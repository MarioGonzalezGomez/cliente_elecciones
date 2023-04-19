package com.mggcode.cliente_elecciones.utils;

import com.mggcode.cliente_elecciones.config.Config;


public class IPFMessageBuilder {

    private static IPFMessageBuilder instance = null;

    String bd = "<databse>";

    private IPFMessageBuilder() {
        this.bd = Config.config.getProperty("BDAutonomicas");
    }

    public static IPFMessageBuilder getInstance() {
        if (instance == null)
            instance = new IPFMessageBuilder();
        return instance;
    }

    public String lateralEntra() {
        return eventRunBuild("LATERAL/ENTRA");
    }

    public String lateralSale() {
        return eventRunBuild("LATERAL/SALE");
    }

    public String lateralActualiza() {
        return eventRunBuild("LATERAL/ACTUALIZO");
    }

    public String faldonMuniEntra() {
        return eventRunBuild("FALDON_MUNI/ENTRA");
    }

    public String faldonMuniSale() {
        return eventRunBuild("FALDON_MUNI/SALE");
    }

    public String faldonMuniEncadena() {
        return eventRunBuild("FALDON_MUNI/ENCADENA");
    }

    public String faldonMuniActualizo() {
        return eventRunBuild("FALDON_MUNI/ACTUALIZO");
    }


    public String faldonAutoEntra() {
        return eventRunBuild("FALDON_AUTO/ENTRA");
    }

    public String faldonAutoSale() {
        return eventRunBuild("FALDON_AUTO/SALE");
    }

    public String faldonAutoEncadena() {
        return eventRunBuild("FALDON_AUTO/ENCADENA");
    }

    public String faldonAutoActualizo() {
        return eventRunBuild("FALDON_AUTO/ACTUALIZO");
    }

    public String autoMuni() {
        return eventRunBuild("AUTO_MUNI/ACTUALIZO");

    }

    public String muniAuto() {
        return eventRunBuild("MUNI_AUTO/ACTUALIZO");

    }

    private String eventRunBuild(String eventName) {
        String message = "";
        String itemSet = "itemset('";
        String eventRun = "EVENT_RUN";
        return message + itemSet +
                bd +
                eventName + "','" + eventRun + "');";
    }
}
