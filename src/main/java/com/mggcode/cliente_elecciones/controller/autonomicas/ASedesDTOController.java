package com.mggcode.cliente_elecciones.controller.autonomicas;

import com.mggcode.cliente_elecciones.DTO.SedesDTO;
import com.mggcode.cliente_elecciones.service.autonomicas.ASedesDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/autonomicas/sedes")
public class ASedesDTOController {

    @Autowired
    private ASedesDTOService sedesDTOService;

    @RequestMapping(path = "/{circunscripcion}/{partido}")
    public String findById(@PathVariable("circunscripcion") String circunscripcion, @PathVariable("partido") String partido, Model model) {
        SedesDTO dto = sedesDTOService.findById(circunscripcion, partido);
        model.addAttribute("sede", dto);
        model.addAttribute("ruta", "/autonomicas/sedes/" + circunscripcion + "/" + partido);
        return "sede";
    }

    @RequestMapping(path = "/{circunscripcion}/{partido}/csv")
    public String findByIdCsv(@PathVariable("circunscripcion") String circunscripcion, @PathVariable("partido") String partido, RedirectAttributes redirectAttributes) throws IOException {
        sedesDTOService.findByIdCsv(circunscripcion, partido);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        return "redirect:/autonomicas/sedes/" + circunscripcion + "/" + partido;
    }


    @RequestMapping(path = "/{circunscripcion}/{partido}/excel")
    public String findByIdExcel(@PathVariable("circunscripcion") String circunscripcion, @PathVariable("partido") String partido, RedirectAttributes redirectAttributes) throws IOException {
        sedesDTOService.findByIdExcel(circunscripcion, partido);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        return "redirect:/autonomicas/sedes/" + circunscripcion + "/" + partido;
    }
}