package com.mggcode.cliente_elecciones.controller.municipales;

import com.mggcode.cliente_elecciones.DTO.SedesDTO;

import com.mggcode.cliente_elecciones.model.Dummy;
import com.mggcode.cliente_elecciones.service.municipales.SedesDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@RestController
@RequestMapping("/municipales/sedes")
public class SedesDTOController {

    @Autowired
    private SedesDTOService sedesDTOService;

    @RequestMapping(path = "/{partido}")
    public ResponseEntity<Dummy> findById(@PathVariable("partido") String partido, Model model) {
        SedesDTO dto = sedesDTOService.findById(partido);
        model.addAttribute("sede", dto);
        model.addAttribute("ruta", "/municipales/sedes/" + partido);
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @RequestMapping(path = "/{partido}/csv")
    public ResponseEntity<Dummy> findByIdCsv(@PathVariable("partido") String partido, RedirectAttributes redirectAttributes) throws IOException {
        sedesDTOService.findByIdCsv(partido);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }


    @RequestMapping(path = "{partido}/excel")
    public ResponseEntity<Dummy> findByIdExcel(@PathVariable("partido") String partido, RedirectAttributes redirectAttributes) throws IOException {
        sedesDTOService.findByIdExcel(partido);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }
}
