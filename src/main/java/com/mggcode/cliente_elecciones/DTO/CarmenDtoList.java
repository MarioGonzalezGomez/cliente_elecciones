package com.mggcode.cliente_elecciones.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CarmenDtoList {
    private int size;
    private List<CarmenDTO> carmenDTOList;
}
