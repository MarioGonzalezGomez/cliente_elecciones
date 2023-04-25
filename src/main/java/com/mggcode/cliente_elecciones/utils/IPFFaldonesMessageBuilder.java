package com.mggcode.cliente_elecciones.utils;


import com.mggcode.cliente_elecciones.config.Config;

public class IPFFaldonesMessageBuilder {

    private static IPFFaldonesMessageBuilder instance = null;

    private final String bd;

    private IPFFaldonesMessageBuilder() {
        Config.getConfiguracion();
        this.bd = Config.config.getProperty("BDFaldones");
        //this.bd = "<FALDONES>";
    }

    public static IPFFaldonesMessageBuilder getInstance() {
        if (instance == null)
            instance = new IPFFaldonesMessageBuilder();
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
