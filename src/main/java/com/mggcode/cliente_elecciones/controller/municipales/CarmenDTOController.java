package com.mggcode.cliente_elecciones.controller.municipales;

import com.mggcode.cliente_elecciones.service.municipales.CarmenDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/municipales/carmen")
public class CarmenDTOController {

    @Autowired
    private CarmenDTOService carmenDTOService;

    @RequestMapping(path = "/{codigo}/csv")
    public String findAllInCsv(@PathVariable("codigo") String codAutonomia) throws IOException {
        carmenDTOService.findAllInCsv(codAutonomia);
        return "Descargado correctamente";
    }

    @RequestMapping(path = "/{codigo}/excel")
    public String findAllInExcel(@PathVariable("codigo") String codAutonomia) throws IOException {
        carmenDTOService.findAllInExcel(codAutonomia);
        return "Descargado correctamente";
    }
}
