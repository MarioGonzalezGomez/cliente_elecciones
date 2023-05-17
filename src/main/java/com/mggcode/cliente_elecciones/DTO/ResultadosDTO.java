package com.mggcode.cliente_elecciones.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultadosDTO {
    private CircunscripcionResultadosDTO ccaa;
    private List<ProvinciaResultadosDTO> provincias;
}
