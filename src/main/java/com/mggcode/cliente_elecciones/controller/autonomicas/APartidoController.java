package com.mggcode.cliente_elecciones.controller.autonomicas;

import com.mggcode.cliente_elecciones.model.Dummy;
import com.mggcode.cliente_elecciones.model.Partido;
import com.mggcode.cliente_elecciones.service.autonomicas.APartidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping("/autonomicas/partidos")
public class APartidoController {

    @Autowired
    private APartidoService partidoService;

    @GetMapping
    public ResponseEntity<Dummy> verPartidos(Model model) {
        List<Partido> partidos = partidoService.findAll();
        model.addAttribute("partidos", partidos);
        model.addAttribute("ruta", "/autonomicas/partidos");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
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

    List<Partido> partidos = new ArrayList<>();
    AtomicBoolean isSuscribed = new AtomicBoolean(false);

    @RequestMapping(path = "/suscribe")
    public void suscribePartidos() {
        if (!isSuscribed.get()) {
            System.out.println("Suscribiendo...");
            isSuscribed.set(true);
            ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
            exec.scheduleAtFixedRate(() -> {
                if (partidos.isEmpty()) {
                    System.out.println("Cargando partidos");
                    partidos = partidoService.findAll();
                } else {
                    System.out.println("Comprobando cambios");
                    var partidosNew = partidoService.findAll();
                    if (!partidosNew.equals(partidos)) {
                        System.out.println("Cambios detectados");
                        //TODO(Hacer aquí las cosas)
                    }
                }
            }, 0, 10, TimeUnit.SECONDS);
        }
    }


}
