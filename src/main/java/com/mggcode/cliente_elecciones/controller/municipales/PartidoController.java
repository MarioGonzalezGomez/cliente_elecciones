package com.mggcode.cliente_elecciones.controller.municipales;

import com.mggcode.cliente_elecciones.model.Dummy;
import com.mggcode.cliente_elecciones.model.Partido;
import com.mggcode.cliente_elecciones.service.municipales.PartidoService;
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
import java.util.List;


@RestController
@RequestMapping("/municipales/partidos")
public class PartidoController {

    @Autowired
    private PartidoService partidoService;

    @GetMapping
    public ResponseEntity<Dummy> verPartidos(Model model) {
        List<Partido> partidos = partidoService.findAll();
        model.addAttribute("partidos", partidos);
        model.addAttribute("ruta", "/municipales/partidos");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Partido> getPartido(@PathVariable("codigo") String cod) {
        return new ResponseEntity<>(partidoService.findById(cod), HttpStatus.OK);
    }

    @RequestMapping(path = "/csv")
    public ResponseEntity<Dummy> findAllInCsv(RedirectAttributes redirectAttributes) throws IOException {
        partidoService.findAllInCsv();
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @RequestMapping(path = "/excel")
    public ResponseEntity<Dummy> findAllInExcel(RedirectAttributes redirectAttributes) throws IOException {
        partidoService.findAllInExcel();
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }


}
