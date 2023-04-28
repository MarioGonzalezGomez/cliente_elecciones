package com.mggcode.cliente_elecciones.utils;

import com.mggcode.cliente_elecciones.config.Config;
import com.mggcode.cliente_elecciones.model.CircunscripcionPartido;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class IPFCartonesMessageBuilder {

    private static IPFCartonesMessageBuilder instance = null;
    private final String bd;
    private final List<Double> aperturas;
    private final List<Double> aperturasDesde;

    private List<CircunscripcionPartido> partidosDentro;

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
        aperturaOficial.put(partido.getKey().getPartido(), Double.parseDouble(apertura));
        return eventRunBuild("OFFSET" + posicionPartido, "MAP_FLOAT_PAR", apertura, 1) +
                eventRunBuild(object, "PRIM_BAR_LEN[2]", apertura, 1);
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
            resultado.append(eventRunBuild(object, "PRIM_BAR_OFFSET[2]", offset + ",0.5", 2));
        }
        return resultado.toString();
    }

    //TODO(Bind fraction a 0)
    private Map<String, Double> calcularOffsets() {
        Map<String, Double> res = new HashMap<>();
        DecimalFormat df = LogicaArcos.getInstance().getFormat();
        LogicaArcos logicaArcos = LogicaArcos.getInstance();
        for (CircunscripcionPartido circunscripcionPartido : partidosDentro) {
        }
        return res;
    }

    private Map<String, Double> calcularAperturas(List<CircunscripcionPartido> partidos) {
        Map<String, Double> res = new HashMap<>();
        return res;
    }

    Map<String, Double> aperturaOficial = new HashMap<>();


    private String pruebaGetOffsetDer(String posicionPartido, List<CircunscripcionPartido> partidos, CircunscripcionPartido partido) {
        Map<String, Double> offsets = new HashMap<>();
        Map<String, Double> aperturasDesde = new HashMap<>();
        Map<String, Double> offsetsDesde = new HashMap<>();


        return "";
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
        // offsets.add(Double.parseDouble(LogicaArcos.getInstance().getApertura(partidos, partido, tipoArco)));
        return eventRunBuild(object, "PRIM_BAR_OFFSET[2]", offset, 2);
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
        return eventRunBuild(object, "PRIM_BAR_OFFSET[2]", "0.0", 1);
    }

    //private String bindReset(String posicionPartido, int tipoArco) {
    //    String object = "";
    //    switch (tipoArco) {
    //        case 1 -> object = "ARCO_OFICIAL/ARCO_OFICIAL_P" + posicionPartido;
    //        case 2 -> object = "ARCO_SONDEO_PRINCIPALES/ARCO_SONDEO_P" + posicionPartido;
    //        case 3 -> object = "ARCO_SONDEO_DESDE/ARCO_SONDEO_P" + posicionPartido;
    //        case 4 -> object = "ARCO_SONDEO_HASTA/ARCO_SONDEO_P" + posicionPartido;
    //        default -> {
    //        }
    //    }
    //    return eventRunBuild(object, "BIND_FRACTION", "0", 1);
    //}

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
        return eventRunBuild(object, "OBJ_CULL", "True", 1);
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
        return eventRunBuild(object, "OBJ_CULL", "False,0,0.4", 2);
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
        String values = "1,0.5,0.4";
        return eventRunBuild(object, "BIND_FRACTION", values, 2);
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
        String offset = getOffsetIzq(posicionPartido, partidos, partido, tipoArco);
        String bindFraction = bindFraction(posicionPartido, tipoArco);

        return objectCull(posicionPartido, tipoArco) + offsetReset + orientacion + apertura + offset + objectCullFalse(posicionPartido, tipoArco) + bindFraction;
    }

    public String partidoEntraDer(List<CircunscripcionPartido> partidos, CircunscripcionPartido partido, int tipoArco) {
        String posicionPartido = String.valueOf((partidos.indexOf(partido) + 1));
        String bindReset = offsetReset(posicionPartido, tipoArco);
        String orientacion = orientacionDer(posicionPartido, tipoArco);
        String apertura = getApertura(posicionPartido, partidos, partido, tipoArco);
        String offset = getOffsetDer(posicionPartido, partidos, partido, tipoArco);
        String bindFraction = bindFraction(posicionPartido, tipoArco);

        return bindReset + orientacion + apertura + offset + bindFraction;
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
