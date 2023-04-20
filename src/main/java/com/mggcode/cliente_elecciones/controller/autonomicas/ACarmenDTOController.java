package com.mggcode.cliente_elecciones.controller.autonomicas;

import com.mggcode.cliente_elecciones.DTO.CarmenDTO;
import com.mggcode.cliente_elecciones.service.autonomicas.ACarmenDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/autonomicas/carmen")
public class ACarmenDTOController {

    @Autowired
    private ACarmenDTOService carmenDTOService;


    @RequestMapping
    public ResponseEntity<List<CarmenDTO>> findAll(){
        var res = carmenDTOService.findAll();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @RequestMapping("/csv")
    public ResponseEntity<String> findAllCsv(RedirectAttributes redirectAttributes){
        var res = carmenDTOService.findAll();
        carmenDTOService.findAllCsv();
        return new ResponseEntity<>("Archivos descargados", HttpStatus.OK);
    }

    @RequestMapping(path = "/{codigo}")
    public String findById(@PathVariable("codigo") String codAutonomia, Model model) {
        CarmenDTO cdto = carmenDTOService.findById(codAutonomia);
        model.addAttribute("carmen", cdto);
        return "carmendtos";
    }

    @RequestMapping(path = "/{codigo}/csv")
    public String findByIdCsv(@PathVariable("codigo") String codCircunscripcion, RedirectAttributes redirectAttributes) throws IOException {
        carmenDTOService.findByIdCsv(codCircunscripcion);
        return "redirect:/autonomicas/carmen/" + codCircunscripcion;
    }


    @RequestMapping(path = "/{codigo}/excel")
    public String findByIdExcel(@PathVariable("codigo") String codAutonomia, RedirectAttributes redirectAttributes) throws IOException {
        carmenDTOService.findByIdExcel(codAutonomia);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        return "redirect:/autonomicas/carmen/" + codAutonomia;
    }

}
