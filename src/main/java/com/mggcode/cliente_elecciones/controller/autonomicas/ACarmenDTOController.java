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


    @RequestMapping(path = "/oficial/{codigo}/{avance}")
    public ResponseEntity<Dummy> findAllOficial(@PathVariable("codigo") String codAutonomia, @PathVariable("avance") String avance, Model model) {
        CarmenDTO cdto = carmenDTOService.findAllOficial(codAutonomia, avance);
        model.addAttribute("carmen", cdto);
        model.addAttribute("ruta", "/autonomicas/carmen/oficial/" + codAutonomia);
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @RequestMapping(path = "/oficial/{codigo}/{avance}/csv")
    public ResponseEntity<Dummy> findAllInCsvOficial(@PathVariable("codigo") String codigo, @PathVariable("avance") String avance, RedirectAttributes redirectAttributes) throws IOException {
        carmenDTOService.findAllInCsvOficial(codigo, avance);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @RequestMapping(path = "/oficial/{codigo}/{avance}/excel")
    public ResponseEntity<Dummy> findAllInExcelOficial(@PathVariable("codigo") String codigo, @PathVariable("avance") String avance, RedirectAttributes redirectAttributes) throws IOException {
        carmenDTOService.findAllInExcelOficial(codigo, avance);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @RequestMapping(path = "/sondeo/{avance}/{codigo}")
    public ResponseEntity<Dummy> findAllSondeo(@PathVariable("codigo") String codigo, @PathVariable("avance") String avance, Model model) {
        CarmenDTO cdto = carmenDTOService.findAllSondeo(codigo, avance);
        model.addAttribute("carmen", cdto);
        model.addAttribute("ruta", "/autonomicas/carmen/sondeo/" + codigo);
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @RequestMapping(path = "/sondeo/{codigo}/{avance}/csv")
    public ResponseEntity<Dummy> findAllInCsvSondeo(@PathVariable("codigo") String codigo, @PathVariable("avance") String avance, RedirectAttributes redirectAttributes) throws IOException {
        carmenDTOService.findAllInCsvSondeo(codigo, avance);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @RequestMapping(path = "/sondeo/{codigo}/{avance}/excel")
    public ResponseEntity<Dummy> findAllInExcelSondeo(@PathVariable("codigo") String codigo, @PathVariable("avance") String avance, RedirectAttributes redirectAttributes) throws IOException {
        carmenDTOService.findAllInExcelSondeo(codigo, avance);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/oficial/{codigo}/{avance}/data")
    public ResponseEntity<CarmenDTO> findDtoByCodigo(@PathVariable("codigo") String codigo, @PathVariable("avance") String avance) {
        return new ResponseEntity<>(carmenDTOService.findAllOficial(codigo, avance), HttpStatus.OK);

    }

    @GetMapping("/sondeo/{codigo}/{avance}/data")
    public ResponseEntity<CarmenDTO> findDtoOficial(@PathVariable("codigo") String codigo, @PathVariable("avance") String avance) {
        return new ResponseEntity<>(carmenDTOService.findAllSondeo(codigo, avance), HttpStatus.OK);
    }

    //  @GetMapping("/sondeo/especial/{codigo}/csv")
    //  public String findDtoSondeoEspecial(@PathVariable("codigo") String codigo, RedirectAttributes redirectAttributes) throws IOException {
    //      carmenDTOService.getSondeoEspecialCsv(codigo);
    //      redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
    //      return "redirect:";
    //  }

}
