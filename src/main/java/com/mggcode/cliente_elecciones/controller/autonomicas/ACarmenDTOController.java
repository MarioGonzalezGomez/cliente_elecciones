package com.mggcode.cliente_elecciones.controller.autonomicas;

import com.mggcode.cliente_elecciones.DTO.CarmenDTO;
import com.mggcode.cliente_elecciones.service.autonomicas.ACarmenDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.IOException;

@Controller
@RequestMapping("/autonomicas/carmen")
public class ACarmenDTOController {

    @Autowired
    private ACarmenDTOService carmenDTOService;

    @RequestMapping(path = "/{codigo}")
    public String findAll(@PathVariable("codigo") String codAutonomia, Model model) throws IOException {
        CarmenDTO cdto = carmenDTOService.findAll(codAutonomia);
        model.addAttribute("carmen", cdto);
        return "carmendtos";
    }

    @RequestMapping(path = "/{codigo}/csv")
    public String findAllInCsv(@PathVariable("codigo") String codCircunscripcion, RedirectAttributes redirectAttributes) throws IOException {
        carmenDTOService.findAllInCsv(codCircunscripcion);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        return "redirect:/autonomicas/carmen/" + codCircunscripcion;
    }

    @RequestMapping(path = "/{codigo}/excel")
    public String findAllInExcel(@PathVariable("codigo") String codAutonomia, RedirectAttributes redirectAttributes) throws IOException {
        carmenDTOService.findAllInExcel(codAutonomia);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        return "redirect:/autonomicas/carmen/" + codAutonomia;
    }

}
