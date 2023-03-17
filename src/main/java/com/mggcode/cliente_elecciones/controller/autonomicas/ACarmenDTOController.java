package com.mggcode.cliente_elecciones.controller.autonomicas;

import com.mggcode.cliente_elecciones.service.autonomicas.ACarmenDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/autonomicas/carmen")
public class ACarmenDTOController {

    @Autowired
    private ACarmenDTOService carmenDTOService;

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
