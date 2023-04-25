package com.mggcode.cliente_elecciones.utils;

import com.mggcode.cliente_elecciones.config.Config;
import com.mggcode.cliente_elecciones.model.CircunscripcionPartido;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Double.NaN;


public class IPFCartonesMessageBuilder {

    private static IPFCartonesMessageBuilder instance = null;
    private final String bd;

    private final List<Double> offsets;
    private final List<Double> offsetsDesde;

    private List<CircunscripcionPartido> partidosDentro;

    private IPFCartonesMessageBuilder() {
        Config.getConfiguracion();
        this.bd = Config.config.getProperty("BDCartones");
        offsets = new ArrayList<>();
        offsets.add(0.0);
        offsetsDesde = new ArrayList<>();
        offsetsDesde.add(0.0);
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

        if (Objects.equals(apertura, String.valueOf(NaN))) {
            apertura = "0.0";
        }
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

        //String offset = "0,0.5";
        //String resultado = eventRunBuild(object, "PRIM_BAR_OFFSET[2]", offset, 2);
        StringBuilder resultado = new StringBuilder();
        DecimalFormat df = LogicaArcos.getInstance().getFormat();
        double sumaAperturas = 0.0;
        String apertura;
        if (partidosDentro.size() != 1) {

            double sumaTotalDesde = offsetsDesde.stream().mapToDouble(Double::doubleValue).sum();
            double sumaTotalHasta = offsets.stream().mapToDouble(Double::doubleValue).sum();

            for (int i = 0; i < partidosDentro.size(); i++) {
                String posicion = String.valueOf((partidos.indexOf(partidosDentro.get(i)) + 1));
                object = object.substring(0, object.length() - 1) + posicion;
                if (tipoArco == 3) {
                    sumaTotalDesde -= offsetsDesde.get(i);
                } else {
                    sumaTotalHasta -= offsets.get(i);
                }
                apertura = df.format(sumaAperturas);
                resultado.append(eventRunBuild(object, "PRIM_BAR_OFFSET[2]", apertura + ",0.5", 2));
            }
        } else {
            resultado.append(eventRunBuild(object, "PRIM_BAR_OFFSET[2]", "0.0,0.5", 2));
        }

      //
      //
      //      double sumaTotalDesde = offsetsDesde.stream().mapToDouble(Double::doubleValue).sum();
      //      double sumaTotalHasta = offsets.stream().mapToDouble(Double::doubleValue).sum();
      //      for (int i = partidosDentro.size() - 1; i >= 0; i--) {
      //          String posicion = String.valueOf((partidos.indexOf(partidosDentro.get(i)) + 1));
      //          object = object.substring(0, object.length() - 1) + posicion;
      //          if (tipoArco == 3) {
      //              sumaTotalDesde -= offsetsDesde.get(i);
      //          } else {
      //              sumaTotalHasta -= offsets.get(i);
      //          }
      //          apertura = df.format(sumaAperturas);
      //          resultado.append(eventRunBuild(object, "PRIM_BAR_OFFSET[2]", apertura + ",0.5", 2));
      //      }
      //  } else {
      //      resultado.append(eventRunBuild(object, "PRIM_BAR_OFFSET[2]", "0.0,0.5", 2));
      //  }


        //String offset2 = df.format(offsets.stream().mapToDouble(Double::doubleValue).sum()) + ",0.5";
        // String offsetipf2 = eventRunBuild(object, "PRIM_BAR_OFFSET[2]", offset1, 2);

        if (tipoArco == 3) {
            double dApertura = Double.parseDouble(LogicaArcos.getInstance().getApertura(partidos, partido, tipoArco));
            if (Double.isNaN(dApertura)) {
                offsetsDesde.add(0.0);
            } else {
                offsetsDesde.add(dApertura);
            }

        } else {
            double dApertura = Double.parseDouble(LogicaArcos.getInstance().getApertura(partidos, partido, tipoArco));
            if (Double.isNaN(dApertura)) {
                offsets.add(0.0);
            } else {
                offsets.add(dApertura);
            }
        }
        return resultado.toString();
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
        offsetsDesde.clear();
        offsetsDesde.add(0.0);
        partidosDentro.clear();
    }

    public void borrarPartido(List<CircunscripcionPartido> partidos, CircunscripcionPartido partido, int tipoArco) {
        offsets.remove(Double.parseDouble(LogicaArcos.getInstance().getApertura(partidos, partido, tipoArco)));
        if (tipoArco == 3) {
            offsetsDesde.remove(Double.parseDouble(LogicaArcos.getInstance().getApertura(partidos, partido, tipoArco)));
        }
        partidosDentro.remove(partido);
    }

    public String partidoEntraIzq(List<CircunscripcionPartido> partidos, CircunscripcionPartido partido, int tipoArco) {
        if (tipoArco != 4) {
            partidosDentro.add(partido);
        }
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
