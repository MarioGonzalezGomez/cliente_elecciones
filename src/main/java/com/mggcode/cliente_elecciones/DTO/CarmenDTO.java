package com.mggcode.cliente_elecciones.DTO;

import com.mggcode.cliente_elecciones.model.Circunscripcion;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CarmenDTO {
    private Circunscripcion circunscripcion;
    private int numPartidos;
    private List<CpDTO> cpDTO;
}
