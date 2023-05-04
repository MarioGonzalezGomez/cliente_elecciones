package com.mggcode.cliente_elecciones.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CarmenDTO {
    private CircunscripcionDTO circunscripcion;
    private int numPartidos;
    private List<CpDTO> cpDTO;
}
