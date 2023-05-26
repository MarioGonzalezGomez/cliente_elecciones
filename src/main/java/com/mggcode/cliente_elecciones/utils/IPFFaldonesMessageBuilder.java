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

    //LATERAL

    public String lateralEntra() {
        return eventRunBuild("LATERAL/ENTRA");
    }

    public String lateralSale() {
        return eventRunBuild("LATERAL/SALE");
    }

    public String lateralActualiza() {
        return eventRunBuild("LATERAL/ACTUALIZO");
    }

    public String lateralDespliega(String codComunidad) {
        return eventRunBuild("LATERAL/" + codComunidad + "/Despliega");
    }

    public String lateralRepliega(String codComunidad) {
        return eventRunBuild("LATERAL/" + codComunidad + "/Repliega");
    }

    public String lateralActualiza(String codComunidad) {
        return eventRunBuild("LATERAL/" + codComunidad + "/Actualiza");
    }

    //FALDON INFERIOR

    public String faldonMuniEntra() {
        return eventRunBuild("FALDON_MUNI/ENTRA");
    }

    public String faldonMuniSale() {
        return eventRunBuild("FALDON_MUNI/SALE");
    }

    public String faldonMuniSondeoEntra() {
        return eventRunBuild("FALDON_MUNI_SONDEO/ENTRA");
    }

    public String faldonMuniSondeoSale() {
        return eventRunBuild("FALDON_MUNI_SONDEO/SALE");
    }

    public String faldonMuniSondeoEncadena() {
        return eventRunBuild("FALDON_MUNI_SONDEO/ENCADENA");
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

    public String faldonAutoSondeoEntra() {
        return eventRunBuild("FALDON_AUTO_SONDEO/ENTRA");
    }

    public String faldonAutoSondeoSale() {
        return eventRunBuild("FALDON_AUTO_SONDEO/SALE");
    }

    public String faldonAutoSondeoEncadena() {
        return eventRunBuild("FALDON_AUTO_SONDEO/ENCADENA");
    }

    public String faldonAutoEncadena() {
        return eventRunBuild("FALDON_AUTO/ENCADENA");
    }

    public String faldonAutoActualizo() {
        return eventRunBuild("FALDON_AUTO/ACTUALIZO");
    }

    public String deMuniAAuto() {
        return eventRunBuild("GIRO/DeMuniAAuto");
    }

    public String deAutoAMuni() {
        return eventRunBuild("GIRO/DeAutoAMuni");
    }

    //ESTANDO EN MUNI

    public String deMuniSondeoAAutoSondeo() {
        return eventRunBuild("GIRO/DeSondeoMuniASondeoAuto");
    }

    public String deMuniSondeoAMuni() {
        return eventRunBuild("GIRO/DeSondeoMuniAMuni");
    }

    public String deMuniSondeoAAuto() {
        return eventRunBuild("GIRO/DeSondeoMuniAAuto");
    }

    public String deMuniASondeoAuto() {
        return eventRunBuild("GIRO/DeMuniASondeoAuto");
    }

    //ESTANDO EN AUTO

    public String deAutoSondeoAMuniSondeo() {
        return eventRunBuild("GIRO/DeSondeoAutoASondeoMuni");
    }

    public String deAutoSondeoAMuni() {
        return eventRunBuild("GIRO/DeSondeoAutoAMuni");
    }

    public String deAutoSondeoAAuto() {
        return eventRunBuild("GIRO/DeSondeoAutoAAuto");
    }

    //ACTUALIZAS

    public String autoMuniActualizo() {
        return eventRunBuild("AUTO_MUNI/ACTUALIZO");
    }

    public String muniAutoActualizo() {
        return eventRunBuild("MUNI_AUTO/ACTUALIZO");
    }

    //VOTANTES

    public String votantesEntra() {
        return eventRunBuild("FALDON_MUNI_VOTANTES/ENTRA");
    }

    public String votantesHistorico() {
        return eventRunBuild("FALDON_MUNI_VOTANTES/ENCADENA");
    }

    public String votantesSale() {
        return eventRunBuild("FALDON_MUNI_VOTANTES/SALE");
    }

    //SEDES

    public String sedesEntra() {
        return eventRunBuild("FALDON_SEDES/ENTRA");
    }

    public String sedesSale() {
        return eventRunBuild("FALDON_SEDES/SALE");
    }


    //PACTOS

    public String pactosEntra() {
        return eventRunBuild("FALDON_PACTOS/ENTRA");
    }

    public String pactosReinicio() {
        return eventRunBuild("FALDON_PACTOS/INICIO");
    }

    public String pactosSale() {
        return eventRunBuild("FALDON_PACTOS/SALE");
    }

    public String pactosEntraDerecha(int posicionPartido) {
        String signal = eventBuild("Pactometro/CualDcha", "MAP_INT_PAR", String.valueOf(posicionPartido), 1);
        signal += eventRunBuild("FALDON_PACTOS/SumaPorDcha");
        return signal;
    }

    public String pactosEntraIzquierda(int posicionPartido) {
        String signal = eventBuild("Pactometro/CualIzda", "MAP_INT_PAR", String.valueOf(posicionPartido), 1);
        signal += eventRunBuild("FALDON_PACTOS/SumaPorIzda");
        return signal;
    }

    //RESET
    public String resetIPF() {
        return eventRunBuild("RESET");
    }

    private String eventRunBuild(String eventName) {
        String message = "";
        String itemSet = "itemset('";
        String eventRun = "EVENT_RUN";
        return message + itemSet +
                bd +
                eventName + "','" + eventRun + "');";
    }

    private String eventBuild(String objecto, String propiedad, String values, int tipoItem) {
        String message = "";
        String itemSet = "";
        if (tipoItem == 1) {
            itemSet = "itemset('";
        }
        if (tipoItem == 2) {
            itemSet = "itemgo('";
        }
        return message + itemSet +
                bd +
                objecto + "','" + propiedad + "'," + values + ");";
    }
}
