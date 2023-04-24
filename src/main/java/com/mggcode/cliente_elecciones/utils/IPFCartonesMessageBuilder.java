package com.mggcode.cliente_elecciones.utils;

import com.mggcode.cliente_elecciones.config.Config;
import com.mggcode.cliente_elecciones.model.CircunscripcionPartido;

import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class IPFCartonesMessageBuilder {

    private static IPFCartonesMessageBuilder instance = null;
    private final String bd;

    private final List<Double> offsets;

    private ArrayDeque<CircunscripcionPartido> partidosDentro;

    private IPFCartonesMessageBuilder() {
        Config.getConfiguracion();
        this.bd = Config.config.getProperty("BDCartones");
        //this.bd = "<CARTONES>";
        offsets = new ArrayList<>();
        offsets.add(0.0);
        partidosDentro = new ArrayDeque<>();
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
        return eventRunBuild(object, "OBJ_ORIENTATION[0]", "90", 1);
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
        return eventRunBuild(object, "OBJ_ORIENTATION[0]", "270", 1);
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
        return eventRunBuild(object, "PRIM_BAR_LEN[2]", apertura, 1);
    }

    private String getOffsetIzq(String posicionPartido, List<CircunscripcionPartido> partidos, CircunscripcionPartido partido, int tipoArco) {
        String object = "";
        switch (tipoArco) {
            case 1 -> object = "ARCO_OFICIAL/PRINCIPALES/PARTIDOS/ARCO_P" + posicionPartido;
            case 2 -> object = "ARCO_SONDEO/ARCO_PRINCIPALES/PARTIDOS/ARCO_P" + posicionPartido;
            case 3 -> object = "ARCO_SONDEO/ARCO_DESDE/PARTIDOS/ARCO_P" + posicionPartido;
            case 4 -> object = "ARCO_SONDEO/ARCO_HASTA/PARTIDOS/ARCO_P" + posicionPartido;
            default -> {
            }
        }

        String offset = "0,0.5";
        AtomicReference<String> resultado = new AtomicReference<>(eventRunBuild(object, "PRIM_BAR_OFFSET[2]", offset, 2));
        DecimalFormat df = LogicaArcos.getInstance().getFormat();
        if(partidosDentro.size()!=1){
            partidosDentro.forEach(par -> {
                resultado.updateAndGet(v -> v + "f");
            });
        }


        String offset2 = df.format(offsets.stream().mapToDouble(Double::doubleValue).sum()) + ",0.5";
       // String offsetipf2 = eventRunBuild(object, "PRIM_BAR_OFFSET[2]", offset1, 2);


        offsets.add(Double.parseDouble(LogicaArcos.getInstance().getApertura(partidos, partido, tipoArco)));
        return resultado.get();
    }

    private String getOffsetDer(String posicionPartido, List<CircunscripcionPartido> partidos, CircunscripcionPartido partido, int tipoArco) {
        String object = "";
        switch (tipoArco) {
            case 1 -> object = "ARCO_OFICIAL/PRINCIPALES/PARTIDOS/ARCO_P" + posicionPartido;
            case 2 -> object = "ARCO_SONDEO/ARCO_PRINCIPALES/PARTIDOS/ARCO_P" + posicionPartido;
            case 3 -> object = "ARCO_SONDEO/ARCO_DESDE/PARTIDOS/ARCO_P" + posicionPartido;
            case 4 -> object = "ARCO_SONDEO/ARCO_HASTA/PARTIDOS/ARCO_P" + posicionPartido;
            default -> {
            }
        }
        DecimalFormat df = LogicaArcos.getInstance().getFormat();
        //String offset = df.format(180.0 - offsets.stream().mapToDouble(Double::doubleValue).sum()) + ",0.5";
        String offset = "180,0.5";
        offsets.add(Double.parseDouble(LogicaArcos.getInstance().getApertura(partidos, partido, tipoArco)));
        return eventRunBuild(object, "PRIM_BAR_OFFSET[2]", offset, 2);
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
        return eventRunBuild(object, "BIND_FRACTION", values, 2);
    }

    public void reset() {
        offsets.clear();
        offsets.add(0.0);

    }

    public void borrarPartido(List<CircunscripcionPartido> partidos, CircunscripcionPartido partido, int tipoArco) {
        offsets.remove(Double.parseDouble(LogicaArcos.getInstance().getApertura(partidos, partido, tipoArco)));
        partidosDentro.remove(partido);
    }

    public String partidoEntraIzq(List<CircunscripcionPartido> partidos, CircunscripcionPartido partido, int tipoArco) {
        partidosDentro.add(partido);
        String posicionPartido = String.valueOf((partidos.indexOf(partido) + 1));

        String orientacion = orientacionIzq(posicionPartido, tipoArco);
        String apertura = getApertura(posicionPartido, partidos, partido, tipoArco);
        String offset = getOffsetIzq(posicionPartido, partidos, partido, tipoArco);
        String bindFraction = bindFraction(posicionPartido, tipoArco);

        return orientacion + apertura + offset + bindFraction;
    }

    public String partidoEntraDer(List<CircunscripcionPartido> partidos, CircunscripcionPartido partido, int tipoArco) {
        String posicionPartido = String.valueOf((partidos.indexOf(partido) + 1));

        String orientacion = orientacionDer(posicionPartido, tipoArco);
        String apertura = getApertura(posicionPartido, partidos, partido, tipoArco);
        String offset = getOffsetDer(posicionPartido, partidos, partido, tipoArco);
        String bindFraction = bindFraction(posicionPartido, tipoArco);

        return orientacion + apertura + offset + bindFraction;
    }


    //Para construir la señal necesitaría el objeto o evento al que llamo, la propiedad a cambiar,
    //el valor o valores que cambian y el tipo: 1 para itemset y 2 para itemgo
    private String eventRunBuild(String objecto, String propiedad, String values, int tipoItem) {
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
