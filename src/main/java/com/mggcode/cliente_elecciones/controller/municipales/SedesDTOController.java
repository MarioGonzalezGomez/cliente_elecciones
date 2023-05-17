package com.mggcode.cliente_elecciones.controller.municipales;

import com.mggcode.cliente_elecciones.DTO.SedesDTO;

import com.mggcode.cliente_elecciones.service.municipales.SedesDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/municipales/sedes")
public class SedesDTOController {

    @Autowired
    private SedesDTOService sedesDTOService;

    @RequestMapping(path = "/{partido}")
    public String findById(@PathVariable("partido") String partido, Model model) {
        SedesDTO dto = sedesDTOService.findById(partido);
        model.addAttribute("sede", dto);
        model.addAttribute("ruta", "/municipales/sedes/" + partido);
        return "sede";
    }

    @RequestMapping(path = "/{partido}/csv")
    public void findByIdCsv(@PathVariable("partido") String partido, RedirectAttributes redirectAttributes) throws IOException {
        sedesDTOService.findByIdCsv(partido);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
    }


    @RequestMapping(path = "{partido}/excel")
    public String findByIdExcel(@PathVariable("partido") String partido, RedirectAttributes redirectAttributes) throws IOException {
        sedesDTOService.findByIdExcel(partido);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        return "redirect:/municipales/sedes/" + partido;
    }
}
