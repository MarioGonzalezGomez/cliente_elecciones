package com.mggcode.cliente_elecciones.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CircunscripcionResultadosDTO {
    private String codigo;
    private String nombre;
    private int numProvincias;
}
