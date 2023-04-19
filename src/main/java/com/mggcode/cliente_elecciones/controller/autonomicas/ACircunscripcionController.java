package com.mggcode.cliente_elecciones.controller.autonomicas;


import com.mggcode.cliente_elecciones.data.Data;
import com.mggcode.cliente_elecciones.model.Circunscripcion;
import com.mggcode.cliente_elecciones.service.autonomicas.ACarmenDTOService;
import com.mggcode.cliente_elecciones.service.autonomicas.ACircunscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;


@Controller
@RequestMapping("/autonomicas/circunscripciones")
public class ACircunscripcionController {

    @Autowired
    private ACircunscripcionService circunscripcionService;

    @Autowired
    private ACarmenDTOService carmenDTOService;

    List<Circunscripcion> changes;


    List<Circunscripcion> circunscripciones = new ArrayList<>();
    AtomicBoolean isSuscribed = new AtomicBoolean(false);


    @GetMapping
    public String verCircunscripciones(Model model) {
        List<Circunscripcion> circunscripciones = circunscripcionService.findAll();
        model.addAttribute("circunscripciones", circunscripciones);
        model.addAttribute("tipo", "autonomicas");
        return "circunscripciones";
    }

    @RequestMapping("/selected/{codigo}")
    public ResponseEntity<String> selectAutonomia(@PathVariable("codigo") String codigo) {
        Data data = Data.getInstance();
        data.setAutonomiaSeleccionada(codigo);
        try {
            lock.lock();
            updateSelected();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }

        return new ResponseEntity<>(codigo, HttpStatus.OK);
    }


    //Descarga todos los csv de autonomía
    @RequestMapping(path = "/csv")
    public String findAllInCsv(RedirectAttributes redirectAttributes) throws IOException {
        circunscripcionService.findAllInCsv();
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        return "redirect:/autonomicas/circunscripciones";
    }

    @RequestMapping(path = "/excel")
    public String findAllInExcel(RedirectAttributes redirectAttributes) throws IOException {
        circunscripcionService.findAllInExcel();
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        return "redirect:/autonomicas/circunscripciones";
    }

    @GetMapping("/{codigo}")
    public String verCircunscripcionDetalle(@PathVariable("codigo") String cod, Model model, @RequestHeader("Referer") String referer) {
        Circunscripcion circunscripcion = circunscripcionService.findById(cod);
        model.addAttribute("circunscripcion", circunscripcion);
        model.addAttribute("referer", referer);
        return "circunscripcionDetalle";
    }

    @RequestMapping(path = "/{codPartido}/csv")
    public String findByIdInCsv(@PathVariable("codPartido") String codPartido) throws IOException {
        circunscripcionService.findByIdInCsv(codPartido);
        return "circunscripcionDetalle";
    }

    @RequestMapping(path = "/{codPartido}/excel")
    public String findByIdInExcel(@PathVariable("codPartido") String codPartido) throws IOException {
        circunscripcionService.findByIdInExcel(codPartido);
        return "circunscripcionDetalle";
    }


    ReentrantLock lock = new ReentrantLock();

    public void suscribeCircunscripciones() {
        if (!isSuscribed.get()) {
            System.out.println("Suscribiendo autonomicas...");
            isSuscribed.set(true);
            ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
            exec.scheduleAtFixedRate(() -> {
                if (circunscripciones.isEmpty()) {
                    System.out.println("Cargando partidos");
                    lock.lock();
                    try {
                        updateAllCsv();
                    } finally {
                        lock.unlock();
                    }

                    circunscripciones = circunscripcionService.findAll();
                } else {
                    // System.out.println("Comprobando cambios autonomicos");
                    List<Circunscripcion> circunscripcionesNew = null;
                    circunscripcionesNew = circunscripcionService.findAll();
                    if (!circunscripcionesNew.equals(circunscripciones)) {
                        System.out.println("Cambios detectados");
                        try {
                            lock.lock();
                            updateAllCsv();
                            //TODO(Hacer el código necesario para ver que se hace con estos cambios)
                            getChanges(circunscripciones, circunscripcionesNew);
                            if (changes.contains(circunscripcionService.findById(data.getAutonomiaSeleccionada()))) {
                                System.out.println("Seleccionada ha cambiado");
                                updateSelected();
                            }
                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                            throw new RuntimeException(e);
                        } finally {
                            lock.unlock();
                        }
                        circunscripciones = circunscripcionesNew;
                    }
                }
            }, 0, 2, TimeUnit.SECONDS);
        }
    }

    Data data = Data.getInstance();

    private void updateAllCsv() {
        carmenDTOService.findAllCsv();
    }

    private void updateSelected() throws IOException {
        carmenDTOService.writeAutonomiaSeleccionada(data.getAutonomiaSeleccionada());
    }

    private List<Circunscripcion> getChanges(List<Circunscripcion> oldList, List<Circunscripcion> newList) {
        List<Circunscripcion> differences = newList.stream()
                .filter(element -> !oldList.contains(element))
                .toList();
        System.out.println(differences);
        System.out.println(data.getAutonomiaSeleccionada());
        changes = differences;
        //Data no funciona bien

        return changes;
    }

    private boolean containsSelected(String codigo) {
        var found = circunscripcionService.findById(codigo);
        return changes.contains(circunscripcionService.findById(codigo));
    }
}