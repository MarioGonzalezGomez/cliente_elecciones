package com.mggcode.cliente_elecciones.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Literal {

    private int id;
    private String castellano;
    private String catalan;
    private String vasco;
    private String gallego;
    private String valenciano;
    private String mallorquin;

}
