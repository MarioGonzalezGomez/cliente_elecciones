package com.mggcode.cliente_elecciones.controller.autonomicas;

import com.mggcode.cliente_elecciones.service.autonomicas.APartidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/autonomicas/partidos")
public class APartidoController {

    @Autowired
    private APartidoService partidoService;

    @RequestMapping(path = "/csv")
    public String findAllInCsv() throws IOException {
        partidoService.findAllInCsv();
        return "Descargado correctamente";
    }

    @RequestMapping(path = "/excel")
    public String findAllInExcel() throws IOException {
        partidoService.findAllInExcel();
        return "Descargado correctamente";
    }

    @RequestMapping(path = "/{codPartido}/csv")
    public String findByIdInCsv(@PathVariable("codPartido") String codPartido) throws IOException {
        partidoService.findByIdInCsv(codPartido);
        return "Descargado correctamente";
    }

    @RequestMapping(path = "/{codPartido}/excel")
    public String findByIdInExcel(@PathVariable("codPartido") String codPartido) throws IOException {
        partidoService.findByIdInExcel(codPartido);
        return "Descargado correctamente";
    }

}
