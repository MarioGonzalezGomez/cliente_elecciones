package com.mggcode.cliente_elecciones.controller.autonomicas;


import com.mggcode.cliente_elecciones.DTO.ResultadosDTO;
import com.mggcode.cliente_elecciones.service.autonomicas.AResultadosDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@RestController
@RequestMapping("/autonomicas/resultados")
public class AResultadosDTOController {

    @Autowired
    private AResultadosDTOService service;

    @RequestMapping(path = "/{circunscripcion}")
    public ResultadosDTO findById(@PathVariable("circunscripcion") String circunscripcion, Model model) {
        ResultadosDTO dto = service.findById(circunscripcion);
        //Introducir objeto en el model para parte gráfica web
        return dto;
    }

    @RequestMapping(path = "/{circunscripcion}/csv")
    public void findByIdCsv(@PathVariable("circunscripcion") String circunscripcion, RedirectAttributes redirectAttributes) throws IOException {
        service.findByIdCsv(circunscripcion);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
    }

}
