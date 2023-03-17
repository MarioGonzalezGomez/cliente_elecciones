package com.mggcode.cliente_elecciones.controller.municipales;


import com.mggcode.cliente_elecciones.service.municipales.CircunscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequestMapping("/municipales/circunscripciones")
public class CircunscripcionController {

    @Autowired
    private CircunscripcionService circunscripcionService;

    @RequestMapping(path = "/csv")
    public String findAllInCsv() throws IOException {
        circunscripcionService.findAllInCsv();
        return "Descargado correctamente";
    }

    @RequestMapping(path = "/excel")
    public String findAllInExcel() throws IOException {
        circunscripcionService.findAllInExcel();
        return "Descargado correctamente";
    }

    @RequestMapping(path = "/{codPartido}/csv")
    public String findByIdInCsv(@PathVariable("codPartido") String codPartido) throws IOException {
        circunscripcionService.findByIdInCsv(codPartido);
        return "Descargado correctamente";
    }

    @RequestMapping(path = "/{codPartido}/excel")
    public String findByIdInExcel(@PathVariable("codPartido") String codPartido) throws IOException {
        circunscripcionService.findByIdInExcel(codPartido);
        return "Descargado correctamente";
    }

}