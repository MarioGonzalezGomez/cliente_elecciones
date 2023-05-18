package com.mggcode.cliente_elecciones.controller.autonomicas;


import com.mggcode.cliente_elecciones.controller.AutonomicasIPF;
import com.mggcode.cliente_elecciones.data.Data;
import com.mggcode.cliente_elecciones.model.Circunscripcion;
import com.mggcode.cliente_elecciones.service.autonomicas.ACarmenDTOService;
import com.mggcode.cliente_elecciones.service.autonomicas.ACircunscripcionService;
import com.mggcode.cliente_elecciones.service.autonomicas.ASedesDTOService;
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

    @Autowired
    private ASedesDTOService sedesDTOService;

    @Autowired
    private AutonomicasIPF ipf;

    List<Circunscripcion> changes;
    List<Circunscripcion> circunscripciones = new ArrayList<>();
    AtomicBoolean isSuscribed = new AtomicBoolean(false);
    ReentrantLock lock = new ReentrantLock();
    AtomicBoolean closeListener = new AtomicBoolean(false);

    Data data = Data.getInstance();
    private boolean oficiales = true;


    @GetMapping
    public String verCircunscripciones(Model model) {
        List<Circunscripcion> circunscripciones = circunscripcionService.findAll();
        model.addAttribute("circunscripciones", circunscripciones);
        model.addAttribute("tipo", "autonomicas");
        model.addAttribute("ruta", "/autonomicas/circunscripciones");
        return "circunscripciones";
    }

    @GetMapping("/selected/oficial/{codigo}")
    public String selectCircunscripcionOficial(@PathVariable("codigo") String codigo, Model model) {
        System.out.println("---" + codigo);
        Data data = Data.getInstance();
        data.setCircunscripcionSeleccionada(codigo);
        oficiales = true;
        try {
            lock.lock();
            updateSelectedOficial();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return "redirect:";

    }

    @GetMapping("/selected/sondeo/{codigo}")
    public String selectCircunscripcionSondeo(@PathVariable("codigo") String codigo, Model model) {
        System.out.println("---" + codigo);
        Data data = Data.getInstance();
        data.setCircunscripcionSeleccionada(codigo);
        oficiales = false;
        try {
            lock.lock();
            updateSelectedSondeo();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return "redirect:";

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

    @GetMapping("/autonomias")
    public ResponseEntity<List<Circunscripcion>> getAutonomias() {
        return new ResponseEntity<>(circunscripcionService.findAutonomias(), HttpStatus.OK);
    }

    @GetMapping("/autonomias/{codigo}")
    public ResponseEntity<List<Circunscripcion>> getCircunscripcionesByAutonomia(@PathVariable("codigo") String codigo) {
        return new ResponseEntity<>(circunscripcionService.findCircunscripcionByAutonomia(codigo), HttpStatus.OK);
    }


    public void suscribeCircunscripciones() {
        if (!isSuscribed.get()) {
            System.out.println("Suscribiendo autonomicas...");
            isSuscribed.set(true);
            ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
            exec.scheduleAtFixedRate(() -> {
                if (circunscripciones.isEmpty()) {
                    System.out.println("Cargando partidos");
                    circunscripciones = circunscripcionService.findAll();
                } else {
                    List<Circunscripcion> circunscripcionesNew;
                    circunscripcionesNew = circunscripcionService.findAll();
                    if (!circunscripcionesNew.equals(circunscripciones)) {
                        System.out.println("Cambios detectados");
                        try {
                            updateAllCsv();
                            ipf.actualizaFaldonLateral();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        getChanges(circunscripciones, circunscripcionesNew);
                        if (containsSelected(data.circunscripcionSeleccionada)) {
                            System.out.println("Seleccionada ha cambiado");
                            try {
                                lock.lock();
                                if (oficiales) {
                                    updateSelectedOficial();
                                } else {
                                    updateSelectedSondeo();
                                }
                            } catch (IOException e) {
                                System.err.println(e.getMessage());
                                throw new RuntimeException(e);
                            } finally {
                                lock.unlock();
                            }
                        }
                        circunscripciones = circunscripcionesNew;
                    }
                }
            }, 0, 2, TimeUnit.SECONDS);
        }
    }

    private void updateAllCsv() throws IOException {
        carmenDTOService.updateAllCsv();
    }

    private void updateSelectedOficial() throws IOException {
        carmenDTOService.writeCricunscripcionSeleccionadaOficial(data.getCircunscripcionSeleccionada());
        carmenDTOService.writeAutonomiaSeleccionadaArcoMayoriasOficial(data.getCircunscripcionSeleccionada());
        ipf.faldonAutoActualizo();
        //carmenDTOService.findAllInCsvOficial(data.getCircunscripcionSeleccionada());


    }

    private void updateSelectedSondeo() throws IOException {
        carmenDTOService.writeCricunscripcionSeleccionadaSondeo(data.getCircunscripcionSeleccionada());
        carmenDTOService.writeAutonomiaSeleccionadaArcoMayoriasSondeo(data.getCircunscripcionSeleccionada());
        //carmenDTOService.findAllInCsvSondeo(data.getCircunscripcionSeleccionada());
        ipf.faldonAutoActualizo();
    }

    private List<Circunscripcion> getChanges(List<Circunscripcion> oldList, List<Circunscripcion> newList) {
        List<Circunscripcion> differences = newList.stream()
                .filter(element -> !oldList.contains(element))
                .toList();
        changes = differences;
        return changes;
    }

    private boolean containsSelected(String codigo) {
        if (codigo.isBlank())
            return false;
        var found = circunscripcionService.findById(codigo);
        return changes.contains(circunscripcionService.findById(codigo));
    }


}