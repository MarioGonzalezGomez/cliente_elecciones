package com.mggcode.cliente_elecciones.utils;

import com.mggcode.cliente_elecciones.config.Config;
import com.mggcode.cliente_elecciones.model.CircunscripcionPartido;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class IPFCartonesMessageBuilder {

    private static IPFCartonesMessageBuilder instance = null;
    private final String bd;
    private final List<Double> aperturas;
    private final List<Double> aperturasDesde;
    private final List<CircunscripcionPartido> partidosDentro;

    private IPFCartonesMessageBuilder() {
        Config.getConfiguracion();
        this.bd = Config.config.getProperty("BDCartones");
        aperturas = new ArrayList<>();
        aperturasDesde = new ArrayList<>();
        partidosDentro = new ArrayList<>();
    }

    public static IPFCartonesMessageBuilder getInstance() {
        if (instance == null)
            instance = new IPFCartonesMessageBuilder();
        return instance;
    }

    private String orientacionIzq(String posicionPartido, int tipoArco) {
        String object = "";
        switch (tipoArco) {
            case 1 -> object = "ARCO_OFICIAL/ARCO_OFICIAL/PRINCIPALES/ARCO_P" + posicionPartido;
            case 2 -> object = "ARCO_SONDEO/ARCO_SONDEO/SONDEO_PRINCIPALES/ARCO_P" + posicionPartido;
            case 3 -> object = "ARCO_SONDEO/ARCO_SONDEO/SONDEO_DESDE/ARCO_P" + posicionPartido;
            case 4 -> object = "ARCO_SONDEO/ARCO_SONDEO/SONDEO_HASTA/ARCO_P" + posicionPartido;
            default -> {
            }
        }
        return eventBuild(object, "OBJ_ORIENTATION[0]", "90", 1);
    }

    private String orientacionDer(String posicionPartido, int tipoArco) {
        String object = "";
        switch (tipoArco) {
            case 1 -> object = "ARCO_OFICIAL/ARCO_OFICIAL/PRINCIPALES/ARCO_P" + posicionPartido;
            case 2 -> object = "ARCO_SONDEO/ARCO_SONDEO/SONDEO_PRINCIPALES/ARCO_P" + posicionPartido;
            case 3 -> object = "ARCO_SONDEO/ARCO_SONDEO/SONDEO_DESDE/ARCO_P" + posicionPartido;
            case 4 -> object = "ARCO_SONDEO/ARCO_SONDEO/SONDEO_HASTA/ARCO_P" + posicionPartido;
            default -> {
            }
        }
        return eventBuild(object, "OBJ_ORIENTATION[0]", "270", 1);
    }

    private String getApertura(String posicionPartido, List<CircunscripcionPartido> partidos, CircunscripcionPartido partido, int tipoArco) {
        String object = "";
        switch (tipoArco) {
            case 1 -> object = "ARCO_OFICIAL/PRINCIPALES/PARTIDOS/ARCO_P" + posicionPartido;
            case 2 -> object = "ARCO_SONDEO/ARCO_PRINCIPALES/PARTIDOS/ARCO_P" + posicionPartido;
            case 3 -> object = "ARCO_SONDEO/ARCO_DESDE/PARTIDOS/ARCO_P" + posicionPartido;
            case 4 -> object = "ARCO_SONDEO/ARCO_HASTA/PARTIDOS/ARCO_P" + posicionPartido;
            default -> {
            }
        }

        String apertura = LogicaArcos.getInstance().getApertura(partidos, partido, tipoArco);
        return eventBuild("OFFSET" + posicionPartido, "MAP_FLOAT_PAR", apertura, 1) +
                eventBuild(object, "PRIM_BAR_LEN[2]", apertura, 1);
    }

    private String getOffset(String posicionPartido, List<CircunscripcionPartido> partidos, CircunscripcionPartido partido, int tipoArco) {
        String object = "";
        switch (tipoArco) {
            case 1 -> object = "ARCO_OFICIAL/PRINCIPALES/PARTIDOS/ARCO_P" + posicionPartido;
            case 2 -> object = "ARCO_SONDEO/ARCO_PRINCIPALES/PARTIDOS/ARCO_P" + posicionPartido;
            case 3 -> object = "ARCO_SONDEO/ARCO_DESDE/PARTIDOS/ARCO_P" + posicionPartido;
            case 4 -> object = "ARCO_SONDEO/ARCO_HASTA/PARTIDOS/ARCO_P" + posicionPartido;
            default -> {
            }
        }
        StringBuilder resultado = new StringBuilder();
        DecimalFormat df = LogicaArcos.getInstance().getFormat();
        String offset;

        //Esto añade a la lista de aperturas el valor del partido actual
        if (tipoArco == 3) {
            aperturasDesde.add(Double.parseDouble(LogicaArcos.getInstance().getApertura(partidos, partido, tipoArco)));
        } else {
            aperturas.add(Double.parseDouble(LogicaArcos.getInstance().getApertura(partidos, partido, tipoArco)));
        }

        //Obtenemos la suma de todas las aperturas, por ejemplo, si tenemos un partido que ocupa 60ª y otro 40ª, la variable valdría 100
        double sumaTotalDesde = aperturasDesde.stream().mapToDouble(Double::doubleValue).sum();
        double sumaTotalHasta = aperturas.stream().mapToDouble(Double::doubleValue).sum();
        for (int i = 0; i < partidosDentro.size(); i++) {
            String posicion = String.valueOf((partidos.indexOf(partidosDentro.get(i)) + 1));
            object = object.substring(0, object.length() - 1) + posicion;

            List<CircunscripcionPartido> sublista = partidosDentro.subList(0, i + 1);
            double aperturasDeAnteriores = sublista.stream().mapToDouble(x -> Double.parseDouble(LogicaArcos.getInstance().getApertura(partidos, x, tipoArco))).sum();

//El offset de un elemento es la suma de todas las aperturas de los elementos posteriores a ella, menos su propia apertura
            if (tipoArco == 3) {
                offset = df.format(sumaTotalDesde - aperturasDeAnteriores);
            } else {
                offset = df.format(sumaTotalHasta - aperturasDeAnteriores);
            }
            if (i == partidosDentro.size() - 1) {
                resultado.append(eventBuild(object, "PRIM_BAR_OFFSET[2]", offset, 1));
            } else {
                resultado.append(eventBuild(object, "PRIM_BAR_OFFSET[2]", offset + ",0.5,0.1", 2));
            }

        }
        return resultado.toString();
    }

    private String offsetReset(String posicionPartido, int tipoArco) {
        String object = "";
        switch (tipoArco) {
            case 1 -> object = "ARCO_OFICIAL/PRINCIPALES/PARTIDOS/ARCO_P" + posicionPartido;
            case 2 -> object = "ARCO_SONDEO/ARCO_PRINCIPALES/PARTIDOS/ARCO_P" + posicionPartido;
            case 3 -> object = "ARCO_SONDEO/ARCO_DESDE/PARTIDOS/ARCO_P" + posicionPartido;
            case 4 -> object = "ARCO_SONDEO/ARCO_HASTA/PARTIDOS/ARCO_P" + posicionPartido;
            default -> {
            }
        }
        return eventBuild(object, "PRIM_BAR_OFFSET[2]", "0.0", 1);
    }

    private String objectCull(String posicionPartido, int tipoArco) {
        String object = "";
        switch (tipoArco) {
            case 1 -> object = "ARCO_OFICIAL/ARCO_OFICIAL/PRINCIPALES/ARCO_P" + posicionPartido;
            case 2 -> object = "ARCO_SONDEO/ARCO_SONDEO/SONDEO_PRINCIPALES/ARCO_P" + posicionPartido;
            case 3 -> object = "ARCO_SONDEO/ARCO_SONDEO/SONDEO_DESDE/ARCO_P" + posicionPartido;
            case 4 -> object = "ARCO_SONDEO/ARCO_SONDEO/SONDEO_HASTA/ARCO_P" + posicionPartido;
            default -> {
            }
        }
        return eventBuild(object, "OBJ_CULL", "True", 1);
    }

    private String objectCullFalse(String posicionPartido, int tipoArco) {
        String object = "";
        switch (tipoArco) {
            case 1 -> object = "ARCO_OFICIAL/ARCO_OFICIAL/PRINCIPALES/ARCO_P" + posicionPartido;
            case 2 -> object = "ARCO_SONDEO/ARCO_SONDEO/SONDEO_PRINCIPALES/ARCO_P" + posicionPartido;
            case 3 -> object = "ARCO_SONDEO/ARCO_SONDEO/SONDEO_DESDE/ARCO_P" + posicionPartido;
            case 4 -> object = "ARCO_SONDEO/ARCO_SONDEO/SONDEO_HASTA/ARCO_P" + posicionPartido;
            default -> {
            }
        }
        return eventBuild(object, "OBJ_CULL", "False,0,0.1", 2);
    }

    private String bindFraction(String posicionPartido, int tipoArco) {
        String object = "";
        switch (tipoArco) {
            case 1 -> object = "ARCO_OFICIAL/ARCO_OFICIAL_P" + posicionPartido;
            case 2 -> object = "ARCO_SONDEO_PRINCIPALES/ARCO_SONDEO_P" + posicionPartido;
            case 3 -> object = "ARCO_SONDEO_DESDE/ARCO_SONDEO_P" + posicionPartido;
            case 4 -> object = "ARCO_SONDEO_HASTA/ARCO_SONDEO_P" + posicionPartido;
            default -> {
            }
        }
        String values = "1,0.5";
        return eventBuild(object, "BIND_FRACTION", values, 2);
    }

    public void reset() {
        aperturas.clear();
        aperturasDesde.clear();
        partidosDentro.clear();
    }

    public void borrarPartido(List<CircunscripcionPartido> partidos, CircunscripcionPartido partido, int tipoArco) {
        aperturas.remove(Double.parseDouble(LogicaArcos.getInstance().getApertura(partidos, partido, tipoArco)));
        if (tipoArco == 3) {
            aperturasDesde.remove(Double.parseDouble(LogicaArcos.getInstance().getApertura(partidos, partido, tipoArco)));
        }
        partidosDentro.remove(partido);
    }

    public String partidoEntraIzq(List<CircunscripcionPartido> partidos, CircunscripcionPartido partido, int tipoArco) {
        if (!partidosDentro.contains(partido)) {
            partidosDentro.add(partido);
        }
        String posicionPartido = String.valueOf((partidos.indexOf(partido) + 1));
        String offsetReset = offsetReset(posicionPartido, tipoArco);
        String orientacion = orientacionIzq(posicionPartido, tipoArco);
        String apertura = getApertura(posicionPartido, partidos, partido, tipoArco);
        String offset = getOffset(posicionPartido, partidos, partido, tipoArco);
        String bindFraction = bindFraction(posicionPartido, tipoArco);

        return objectCull(posicionPartido, tipoArco) + offsetReset + orientacion + apertura + offset + objectCullFalse(posicionPartido, tipoArco) + bindFraction;
    }

    public String partidoEntraDer(List<CircunscripcionPartido> partidos, CircunscripcionPartido partido, int tipoArco) {
        if (!partidosDentro.contains(partido)) {
            partidosDentro.add(partido);
        }
        String posicionPartido = String.valueOf((partidos.indexOf(partido) + 1));
        String offsetReset = offsetReset(posicionPartido, tipoArco);
        String orientacion = orientacionDer(posicionPartido, tipoArco);
        String apertura = getApertura(posicionPartido, partidos, partido, tipoArco);
        String offset = getOffset(posicionPartido, partidos, partido, tipoArco);
        String bindFraction = bindFraction(posicionPartido, tipoArco);

        return objectCull(posicionPartido, tipoArco) + offsetReset + orientacion + apertura + offset + objectCullFalse(posicionPartido, tipoArco) + bindFraction;
    }

    public String arcoEntra() {
        return eventRunBuild("ARCO/ENTRA", false);
    }

    public String arcoEntraDelay() {
        return eventRunBuild("ARCO/ENTRA", true);
    }

    public String arcoSale() {
        return eventRunBuild("ARCO/SALE", false);
    }

    public String arcoPactos() {
        return eventRunBuild("ARCO/PACTOS", false);
    }

    public String arcoSondeoEntra() {
        return eventRunBuild("ARCO_SONDEO/ENTRA", false);
    }

    public String arcoSondeoEntraDelay() {
        return eventRunBuild("ARCO_SONDEO/ENTRA", true);
    }

    public String arcoSondeoSale() {
        return eventRunBuild("ARCO_SONDEO/SALE", false);
    }

    public String arcoSondeoPactos() {
        return eventRunBuild("ARCO_SONDEO/PACTOS", false);
    }

    public String participacionEntra() {
        return eventRunBuild("PARTICIPACION/ENTRA", false);
    }

    public String participacionEspAuto() {
        return eventRunBuild("ENTRA_ESP_AUTONOMICA", false);
    }
    public String participacionEspAutoDelay() {
        return eventRunBuild("ENTRA_ESP_AUTONOMICA", true);
    }

    public String participacionEspMuni() {
        return eventRunBuild("ENTRA_ESP_MUNICIPAL", false);
    }
    public String participacionEspMuniDelay() {
        return eventRunBuild("ENTRA_ESP_MUNICIPAL", true);
    }

    public String saleParticipacionEsp() {
        return eventRunBuild("SALE_ESP", false);
    }

    public String participacionEntraDelay() {
        return eventRunBuild("PARTICIPACION/ENTRA", true);
    }

    public String participacionSale() {
        return eventRunBuild("PARTICIPACION/SALE", false);
    }

    public String participacionCambiaAuto() {
        return eventRunBuild("PARTICIPACION/CAMBIA_COMUNIDAD", false);
    }

    public String participacionCambiaMuni() {
        return eventRunBuild("PARTICIPACION/CAMBIA_MUNICIPIO", false);
    }

    public String resultadosEntra() {
        return eventRunBuild("RESULTADOS/ENTRA", false);
    }

    public String resultadosEntraDelay() {
        return eventRunBuild("RESULTADOS/ENTRA", true);
    }

    public String resultadosSale() {
        return eventRunBuild("RESULTADOS/SALE", false);
    }

    public String resultadosCambiaAuto() {
        return eventRunBuild("RESULTADOS/CAMBIA", false);
    }

    public String resultadosCambiaMuni() {
        return eventRunBuild("RESULTADOS/CAMBIA", false);
    }

    public String resultadosSondeoEntra() {
        return eventRunBuild("RESULTADOS_SONDEO/ENTRA", false);
    }

    public String resultadosSondeoEntraDelay() {
        return eventRunBuild("RESULTADOS_SONDEO/ENTRA", true);
    }

    public String resultadosSondeoSale() {
        return eventRunBuild("RESULTADOS_SONDEO/SALE", false);
    }

    public String resultadosSondeoCambiaAuto() {
        return eventRunBuild("RESULTADOS_SONDEO/CAMBIA", false);
    }

    public String resultadosSondeoCambiaMuni() {
        return eventRunBuild("RESULTADOS_SONDEO/CAMBIA", false);
    }

    public String load() {
        return eventRunBuild("LOAD", false);
    }

    public String resetIPF() {
        return eventRunBuild("RESET", false);
    }


    //Para construir la señal necesitaría el objeto o evento al que llamo, la propiedad a cambiar,
    //el valor o valores que cambian y el tipo: 1 para itemset y 2 para itemgo
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

    private String eventRunBuild(String objecto, boolean delay) {
        if (!delay) {
            String itemSet = "itemset('";
            return itemSet + bd + objecto + "','" + "EVENT_RUN" + "');";
        } else {
            String itemSet = "itemgo('";
            return itemSet + bd + objecto + "','" + "EVENT_RUN',0,1.5" + ");";
        }
    }


}
