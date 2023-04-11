package com.mggcode.cliente_elecciones.controller.municipales;

import com.mggcode.cliente_elecciones.model.Partido;
import com.mggcode.cliente_elecciones.service.municipales.PartidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/municipales/partidos")
public class PartidoController {

    @Autowired
    private PartidoService partidoService;

    @GetMapping
    public String verPartidos(Model model) {
        List<Partido> partidos = partidoService.findAll();
        model.addAttribute("partidos", partidos);
        return "partidos";
    }

    @RequestMapping(path = "/csv")
    public String findAllInCsv(RedirectAttributes redirectAttributes) throws IOException {
        partidoService.findAllInCsv();
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        return "redirect:/municipales/partidos";
    }

    @RequestMapping(path = "/excel")
    public String findAllInExcel(RedirectAttributes redirectAttributes) throws IOException {
        partidoService.findAllInExcel();
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        return "redirect:/municipales/partidos";
    }

}
