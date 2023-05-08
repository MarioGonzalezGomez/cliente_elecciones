package com.mggcode.cliente_elecciones.model;

import com.mggcode.cliente_elecciones.DTO.CircunscripcionDTO;
import com.mggcode.cliente_elecciones.DTO.CpDTO;
import lombok.Data;

@Data
public class CircunscripcionPartido {

    private Key key;

    private int escanos_desde;

    private int escanos_hasta;

    private double porcentajeVoto;

    private int numVotantes;

    private int escanos_desde_hist;

    private int escanos_hasta_hist;

    private double votantesHistorico;

    private int numVotantesHistorico;

    private int escanos_desde_sondeo;

    private int escanos_hasta_sondeo;

    private double porcentajeVotoSondeo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CircunscripcionPartido that = (CircunscripcionPartido) o;

        if (escanos_desde != that.escanos_desde) return false;
        if (escanos_hasta != that.escanos_hasta) return false;
        if (Double.compare(that.porcentajeVoto, porcentajeVoto) != 0) return false;
        if (numVotantes != that.numVotantes) return false;
        if (escanos_desde_hist != that.escanos_desde_hist) return false;
        if (escanos_hasta_hist != that.escanos_hasta_hist) return false;
        if (Double.compare(that.votantesHistorico, votantesHistorico) != 0) return false;
        if (numVotantesHistorico != that.numVotantesHistorico) return false;
        if (escanos_desde_sondeo != that.escanos_desde_sondeo) return false;
        if (escanos_hasta_sondeo != that.escanos_hasta_sondeo) return false;
        if (Double.compare(that.porcentajeVotoSondeo, porcentajeVotoSondeo) != 0) return false;
        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = key.hashCode();
        result = 31 * result + escanos_desde;
        result = 31 * result + escanos_hasta;
        temp = Double.doubleToLongBits(porcentajeVoto);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + numVotantes;
        result = 31 * result + escanos_desde_hist;
        result = 31 * result + escanos_hasta_hist;
        temp = Double.doubleToLongBits(votantesHistorico);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + numVotantesHistorico;
        result = 31 * result + escanos_desde_sondeo;
        result = 31 * result + escanos_hasta_sondeo;
        temp = Double.doubleToLongBits(porcentajeVotoSondeo);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public static CircunscripcionPartido mapFromCpDTO(CircunscripcionDTO circunscripcion, CpDTO cpDTO) {
        CircunscripcionPartido circunscripcionPartido = new CircunscripcionPartido();

        Key key = new Key();
        key.setPartido(cpDTO.getCodigoPartido());
        key.setCircunscripcion(circunscripcion.getCodigo());
        circunscripcionPartido.setKey(key);

        circunscripcionPartido.setEscanos_desde(cpDTO.getEscanos_desde());
        circunscripcionPartido.setEscanos_hasta(cpDTO.getEscanos_hasta());
        circunscripcionPartido.setEscanos_hasta_hist(cpDTO.getEscanos_hasta_hist());
        circunscripcionPartido.setPorcentajeVoto(cpDTO.getPorcentajeVoto());
        circunscripcionPartido.setVotantesHistorico(cpDTO.getPorcentajeVotoHistorico());
        circunscripcionPartido.setNumVotantes(cpDTO.getNumVotantes());
        return circunscripcionPartido;
    }

    public CircunscripcionPartido(){

    }
}
