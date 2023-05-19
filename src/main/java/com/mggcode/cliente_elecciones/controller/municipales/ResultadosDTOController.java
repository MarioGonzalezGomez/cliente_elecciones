package com.mggcode.cliente_elecciones.controller.municipales;


import com.mggcode.cliente_elecciones.DTO.ResultadosDTO;
import com.mggcode.cliente_elecciones.service.municipales.ResultadosDTOService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/municipales/resultados")
public class ResultadosDTOController {

    @Autowired
    private ResultadosDTOService service;

    @RequestMapping(path = "/{circunscripcion}")
    public ResultadosDTO findById(@PathVariable("circunscripcion") String circunscripcion) {
        ResultadosDTO dto = service.findById(circunscripcion);
        //Introducir objeto en el model para parte gráfica web
        return dto;
    }

    @RequestMapping(path = "/{circunscripcion}/csv")
    public String findByIdCsv(@PathVariable("circunscripcion") String circunscripcion, RedirectAttributes redirectAttributes) throws IOException {
        service.findByIdCsv(circunscripcion);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        return "redirect:";
    }
}
