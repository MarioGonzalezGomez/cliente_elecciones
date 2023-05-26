package com.mggcode.cliente_elecciones.controller.autonomicas;

import com.mggcode.cliente_elecciones.DTO.CarmenDTO;
import com.mggcode.cliente_elecciones.model.Dummy;
import com.mggcode.cliente_elecciones.service.autonomicas.ACarmenDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;


@RestController
@RequestMapping("/autonomicas/carmen")
public class ACarmenDTOController {

    @Autowired
    private ACarmenDTOService carmenDTOService;


    @RequestMapping(path = "/oficial/{codigo}")
    public ResponseEntity<Dummy> findAllOficial(@PathVariable("codigo") String codAutonomia, Model model) {
        CarmenDTO cdto = carmenDTOService.findAllOficial(codAutonomia);
        model.addAttribute("carmen", cdto);
        model.addAttribute("ruta", "/autonomicas/carmen/oficial/" + codAutonomia);
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @RequestMapping(path = "/oficial/{codigo}/csv")
    public ResponseEntity<Dummy> findAllInCsvOficial(@PathVariable("codigo") String codCircunscripcion, RedirectAttributes redirectAttributes) throws IOException {
        carmenDTOService.findAllInCsvOficial(codCircunscripcion);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @RequestMapping(path = "/oficial/{codigo}/excel")
    public ResponseEntity<Dummy> findAllInExcelOficial(@PathVariable("codigo") String codAutonomia, RedirectAttributes redirectAttributes) throws IOException {
        carmenDTOService.findAllInExcelOficial(codAutonomia);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @RequestMapping(path = "/sondeo/{codigo}")
    public ResponseEntity<Dummy> findAllSondeo(@PathVariable("codigo") String codAutonomia, Model model) {
        CarmenDTO cdto = carmenDTOService.findAllSondeo(codAutonomia);
        model.addAttribute("carmen", cdto);
        model.addAttribute("ruta", "/autonomicas/carmen/sondeo/" + codAutonomia);
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @RequestMapping(path = "/sondeo/{codigo}/csv")
    public ResponseEntity<Dummy> findAllInCsvSondeo(@PathVariable("codigo") String codCircunscripcion, RedirectAttributes redirectAttributes) throws IOException {
        carmenDTOService.findAllInCsvSondeo(codCircunscripcion);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @RequestMapping(path = "/sondeo/{codigo}/excel")
    public ResponseEntity<Dummy> findAllInExcelSondeo(@PathVariable("codigo") String codAutonomia, RedirectAttributes redirectAttributes) throws IOException {
        carmenDTOService.findAllInExcelSondeo(codAutonomia);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/oficial/{codigo}/data")
    public ResponseEntity<CarmenDTO> findDtoByCodigo(@PathVariable("codigo") String codigo) {
        return new ResponseEntity<>(carmenDTOService.findAllOficial(codigo), HttpStatus.OK);

    }

    @GetMapping("/sondeo/{codigo}/data")
    public ResponseEntity<CarmenDTO> findDtoOficial(@PathVariable("codigo") String codigo) {
        return new ResponseEntity<>(carmenDTOService.findAllSondeo(codigo), HttpStatus.OK);
    }

    //  @GetMapping("/sondeo/especial/{codigo}/csv")
    //  public String findDtoSondeoEspecial(@PathVariable("codigo") String codigo, RedirectAttributes redirectAttributes) throws IOException {
    //      carmenDTOService.getSondeoEspecialCsv(codigo);
    //      redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
    //      return "redirect:";
    //  }

}
