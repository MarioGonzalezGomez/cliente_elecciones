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

    @RequestMapping(path = "/oficial/{circunscripcion}")
    public ResultadosDTO findByIdOficial(@PathVariable("circunscripcion") String circunscripcion) {
        ResultadosDTO dto = service.findByIdOficial(circunscripcion);
        //Introducir objeto en el model para parte gráfica web
        return dto;
    }

    @RequestMapping(path = "/sondeo/{circunscripcion}")
    public ResultadosDTO findByIdSondeo(@PathVariable("circunscripcion") String circunscripcion) {
        ResultadosDTO dto = service.findByIdSondeo(circunscripcion);
        //Introducir objeto en el model para parte gráfica web
        return dto;
    }

    @RequestMapping(path = "/oficial/{circunscripcion}/csv")
    public String findByIdCsvOficial(@PathVariable("circunscripcion") String circunscripcion, RedirectAttributes redirectAttributes) throws IOException {
        service.findByIdCsvOficial(circunscripcion);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        return "redirect:";
    }
    @RequestMapping(path = "/sondeo/{circunscripcion}/csv")
    public String findByIdCsvSondeo(@PathVariable("circunscripcion") String circunscripcion, RedirectAttributes redirectAttributes) throws IOException {
        service.findByIdCsvSondeo(circunscripcion);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        return "redirect:";
    }
}
