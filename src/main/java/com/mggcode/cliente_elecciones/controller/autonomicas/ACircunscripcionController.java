package com.mggcode.cliente_elecciones.controller.autonomicas;


import com.mggcode.cliente_elecciones.ClienteEleccionesApplication;
import com.mggcode.cliente_elecciones.controller.AutonomicasIPF;
import com.mggcode.cliente_elecciones.data.Data;
import com.mggcode.cliente_elecciones.model.Circunscripcion;
import com.mggcode.cliente_elecciones.model.Dummy;
import com.mggcode.cliente_elecciones.service.autonomicas.ACarmenDTOService;
import com.mggcode.cliente_elecciones.service.autonomicas.ACircunscripcionService;
import com.mggcode.cliente_elecciones.service.autonomicas.AResultadosDTOService;
import com.mggcode.cliente_elecciones.service.autonomicas.ASedesDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;


@RestController
@RequestMapping("/autonomicas/circunscripciones")
public class ACircunscripcionController {

    @Autowired
    private ACircunscripcionService circunscripcionService;

    @Autowired
    private ACarmenDTOService carmenDTOService;

    @Autowired
    private ASedesDTOService sedesDTOService;

    @Autowired
    private AResultadosDTOService resultadosDTOService;

    @Autowired
    private AutonomicasIPF ipf;

    List<Circunscripcion> changes;
    List<Circunscripcion> circunscripciones = new ArrayList<>();
    AtomicBoolean isSuscribed = new AtomicBoolean(false);
    ReentrantLock lock = new ReentrantLock();
    AtomicBoolean closeListener = new AtomicBoolean(false);

    Data data = Data.getInstance();
    private boolean oficiales = true;


    private String avance = "1";


    @GetMapping
    public ResponseEntity<Dummy> verCircunscripciones(Model model) {
        List<Circunscripcion> circunscripciones = circunscripcionService.findAll();
        model.addAttribute("circunscripciones", circunscripciones);
        model.addAttribute("tipo", "autonomicas");
        model.addAttribute("ruta", "/autonomicas/circunscripciones");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/selected/oficial/f_autonomicas/{codigo}/{avance}")
    public ResponseEntity<Dummy> selectCircunscripcionAutonomiaOficial(@PathVariable("codigo") String codigo, @PathVariable("avance") String avance) {
        System.out.println("---" + codigo);
        Data data = Data.getInstance();
        data.setCircunscripcionSeleccionada(codigo);
        oficiales = true;
        try {
            lock.lock();
            carmenDTOService.writeCricunscripcionSeleccionadaOficial(data.getCircunscripcionSeleccionada(), avance);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        this.avance = avance;
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/selected/oficial/mapa_mayorias/{codigo}/{avance}")
    public ResponseEntity<Dummy> selectCircunscripcionMapaOficial(@PathVariable("codigo") String codigo, @PathVariable("avance") String avance) {
        System.out.println("---" + codigo);
        Data data = Data.getInstance();
        data.setCircunscripcionSeleccionada(codigo);
        oficiales = true;
        try {
            lock.lock();

            carmenDTOService.writeAutonomiaSeleccionadaArcoMayoriasOficial(data.getCircunscripcionSeleccionada(), avance);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        this.avance = avance;
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/selected/sondeo/f_autonomicas/{codigo}/{avance}")
    public ResponseEntity<Dummy> selectCircunscripcionAutnomiaSondeo(@PathVariable("codigo") String codigo, @PathVariable("avance") String avance) {
        System.out.println("---" + codigo);
        Data data = Data.getInstance();
        data.setCircunscripcionSeleccionada(codigo);
        oficiales = false;
        try {
            lock.lock();

            carmenDTOService.writeCricunscripcionSeleccionadaSondeo(data.getCircunscripcionSeleccionada(), avance);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        this.avance = avance;
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/selected/sondeo/mapa_mayorias/{codigo}/{avance}")
    public ResponseEntity<Dummy> selectCircunscripcionMapaSondeo(@PathVariable("codigo") String codigo, @PathVariable("avance") String avance) {
        System.out.println("---" + codigo);
        Data data = Data.getInstance();
        data.setCircunscripcionSeleccionada(codigo);
        oficiales = false;
        try {
            lock.lock();

            carmenDTOService.writeAutonomiaSeleccionadaArcoMayoriasSondeo(data.getCircunscripcionSeleccionada(), avance);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        this.avance = avance;
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/update")
    public ResponseEntity<Dummy> update() throws IOException {
        updateAllCsv();
        ipf.actualizaFaldonLateral();
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }


    //Descarga todos los csv de autonomía
    @RequestMapping(path = "/csv")
    public ResponseEntity<Dummy> findAllInCsv(RedirectAttributes redirectAttributes) throws IOException {
        circunscripcionService.findAllInCsv();
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @RequestMapping(path = "/excel")
    public ResponseEntity<Dummy> findAllInExcel(RedirectAttributes redirectAttributes) throws IOException {
        circunscripcionService.findAllInExcel();
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Dummy> verCircunscripcionDetalle(@PathVariable("codigo") String cod, @RequestHeader("Referer") String referer) {
        Circunscripcion circunscripcion = circunscripcionService.findById(cod);
        //model.addAttribute("circunscripcion", circunscripcion);
        //model.addAttribute("referer", referer);
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @RequestMapping(path = "/{codPartido}/csv")
    public ResponseEntity<Dummy> findByIdInCsv(@PathVariable("codPartido") String codPartido) throws IOException {
        circunscripcionService.findByIdInCsv(codPartido);
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @RequestMapping(path = "/{codPartido}/excel")
    public ResponseEntity<Dummy> findByIdInExcel(@PathVariable("codPartido") String codPartido) throws IOException {
        circunscripcionService.findByIdInExcel(codPartido);
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/autonomias")
    public ResponseEntity<List<Circunscripcion>> getAutonomias() {
        return new ResponseEntity<>(circunscripcionService.findAutonomias(), HttpStatus.OK);
    }

    @GetMapping("/autonomias/{codigo}")
    public ResponseEntity<List<Circunscripcion>> getCircunscripcionesByAutonomia(@PathVariable("codigo") String codigo) {
        return new ResponseEntity<>(circunscripcionService.findCircunscripcionByAutonomia(codigo), HttpStatus.OK);
    }

    @GetMapping("/filtrada/{codigo}")
    public ResponseEntity<List<Circunscripcion>> filtradasPorMostrar(@PathVariable("codigo") String codigo) {
        List<Circunscripcion> circunscripcions = circunscripcionService.filtradasPorMostrar();
        List<Circunscripcion> resultado = circunscripcions.stream().filter(c -> c.getCodigoComunidad().equals(codigo)).toList();
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }


    public void suscribeCircunscripciones() {
        if (!isSuscribed.get()) {
            System.out.println("Suscribiendo autonomicas...");
            isSuscribed.set(true);
            ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
            exec.scheduleAtFixedRate(() -> {
                if (!ClienteEleccionesApplication.closeCheck) {
                    System.out.println("Cerrando...");
                    exec.shutdown();
                }
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
                                    ipf.cartonesActualiza();
                                } else {
                                    updateSelectedSondeo();
                                    ipf.cartonesActualiza();
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
            }, 0, 30, TimeUnit.SECONDS);
        }
    }

    private void updateAllCsv() throws IOException {
        carmenDTOService.updateAllCsv(avance);
    }

    private void updateSelectedOficial() throws IOException {
        carmenDTOService.writeCricunscripcionSeleccionadaOficial(data.getCircunscripcionSeleccionada(), avance);
        carmenDTOService.writeAutonomiaSeleccionadaArcoMayoriasOficial(data.getCircunscripcionSeleccionada(), avance);
        resultadosDTOService.findByIdCsvOficial(data.getCircunscripcionSeleccionada());
    }

    private void updateSelectedSondeo() throws IOException {
        carmenDTOService.writeCricunscripcionSeleccionadaSondeo(data.getCircunscripcionSeleccionada(), avance);
        carmenDTOService.writeAutonomiaSeleccionadaArcoMayoriasSondeo(data.getCircunscripcionSeleccionada(), avance);
        resultadosDTOService.findByIdCsvSondeo(data.getCircunscripcionSeleccionada());
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