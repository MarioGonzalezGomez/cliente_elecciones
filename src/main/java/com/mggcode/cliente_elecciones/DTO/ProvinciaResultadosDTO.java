package com.mggcode.cliente_elecciones.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProvinciaResultadosDTO {
    private String codigo;
    private String nombre;
    private String codPartidoGanador;
    private String nomPartidoGanador;
}
