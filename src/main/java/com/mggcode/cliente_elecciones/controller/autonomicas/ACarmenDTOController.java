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


    @RequestMapping(path = "/oficial/{codigo}")
    public String findAllOficial(@PathVariable("codigo") String codAutonomia, Model model) {
        CarmenDTO cdto = carmenDTOService.findAllOficial(codAutonomia);
        model.addAttribute("carmen", cdto);
        model.addAttribute("ruta", "/autonomicas/carmen/oficial/" + codAutonomia);
        return "carmendtos";
    }

    @RequestMapping(path = "/oficial/{codigo}/csv")
    public String findAllInCsvOficial(@PathVariable("codigo") String codCircunscripcion, RedirectAttributes redirectAttributes) throws IOException {
        carmenDTOService.findAllInCsvOficial(codCircunscripcion);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        return "redirect:/autonomicas/carmen/oficial/" + codCircunscripcion;
    }

    @RequestMapping(path = "/oficial/{codigo}/excel")
    public String findAllInExcelOficial(@PathVariable("codigo") String codAutonomia, RedirectAttributes redirectAttributes) throws IOException {
        carmenDTOService.findAllInExcelOficial(codAutonomia);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        return "redirect:/autonomicas/carmen/oficial/" + codAutonomia;
    }

    @RequestMapping(path = "/sondeo/{codigo}")
    public String findAllSondeo(@PathVariable("codigo") String codAutonomia, Model model) {
        CarmenDTO cdto = carmenDTOService.findAllSondeo(codAutonomia);
        model.addAttribute("carmen", cdto);
        model.addAttribute("ruta", "/autonomicas/carmen/sondeo/" + codAutonomia);
        return "carmendtos";
    }

    @RequestMapping(path = "/sondeo/{codigo}/csv")
    public String findAllInCsvSondeo(@PathVariable("codigo") String codCircunscripcion, RedirectAttributes redirectAttributes) throws IOException {
        carmenDTOService.findAllInCsvSondeo(codCircunscripcion);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        return "redirect:/autonomicas/carmen/sondeo/" + codCircunscripcion;
    }

    @RequestMapping(path = "/sondeo/{codigo}/excel")
    public String findAllInExcelSondeo(@PathVariable("codigo") String codAutonomia, RedirectAttributes redirectAttributes) throws IOException {
        carmenDTOService.findAllInExcelSondeo(codAutonomia);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        return "redirect:/autonomicas/carmen/sondeo/" + codAutonomia;
    }


}
