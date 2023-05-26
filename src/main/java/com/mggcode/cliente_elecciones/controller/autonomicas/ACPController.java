package com.mggcode.cliente_elecciones.controller.autonomicas;

import com.mggcode.cliente_elecciones.model.CircunscripcionPartido;
import com.mggcode.cliente_elecciones.model.Dummy;
import com.mggcode.cliente_elecciones.service.autonomicas.ACircunscripcionPartidoService;
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

@RestController
@RequestMapping("/autonomicas/cp")
public class ACPController {
    @Autowired
    private ACircunscripcionPartidoService cpService;


    List<CircunscripcionPartido> circunscripcionPartidos = new ArrayList<>();
    AtomicBoolean isSuscribed = new AtomicBoolean(false);
    List<CircunscripcionPartido> changes;

    @GetMapping
    public ResponseEntity<Dummy> verCPS(Model model) {
        List<CircunscripcionPartido> cps = cpService.findAll();
        model.addAttribute("cps", cps);
        model.addAttribute("ruta", "/autonomicas/cp");
        model.addAttribute("tipo", "autonomicas");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @RequestMapping(path = "/csv")
    public ResponseEntity<Dummy> findAllInCsv(RedirectAttributes redirectAttributes) throws IOException {
        cpService.findAllInCsv();
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @RequestMapping(path = "/excel")
    public ResponseEntity<Dummy> findAllInExcel(RedirectAttributes redirectAttributes) throws IOException {
        cpService.findAllInExcel();
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/{codigoCir}/{codigoPar}")
    public ResponseEntity<Dummy> verCircunscripcionDetalle(@PathVariable("codigoCir") String codCir, @PathVariable("codigoPar") String codPar, Model model, @RequestHeader("Referer") String referer) {
        CircunscripcionPartido cp = cpService.findById(codCir, codPar);
        model.addAttribute("cp", cp);
        model.addAttribute("referer", referer);
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    //Todos los partidos dentro de una circunscripción
    @GetMapping("/cp/circunscripcion/{codigo}")
    public ResponseEntity<Dummy> verTodosCPDeUnaCircunscripcion(@PathVariable("codigo") String cod, Model model) {
        List<CircunscripcionPartido> cps = cpService.findByIdCircunscripcion(cod);
        model.addAttribute("cps", cps);
        model.addAttribute("ruta", "/autonomicas/cp/circunscripcion/" + cod);
        model.addAttribute("tipo", "autonomicas");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    //Grupo de Mayorías (A nivel Nacional y Autonómico)

    @GetMapping("/mayorias/autonomias")
    public ResponseEntity<Dummy> verCPSporMA(Model model) {
        List<CircunscripcionPartido> cps = cpService.masVotadosPorAutonomia();
        model.addAttribute("cps", cps);
        model.addAttribute("ruta", "/autonomicas/cp/mayorias/autonomias");
        model.addAttribute("tipo", "autonomicas");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/mayorias/provincias")
    public ResponseEntity<Dummy> verCPSporMP(Model model) {
        List<CircunscripcionPartido> cps = cpService.masVotadosPorProvincia();
        model.addAttribute("cps", cps);
        model.addAttribute("ruta", "/autonomicas/cp/mayorias/provincias");
        model.addAttribute("tipo", "autonomicas");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/mayorias/{codigo}")
    public ResponseEntity<Dummy> verMayoriasAutonomicas(@PathVariable("codigo") String cod, Model model) {
        List<CircunscripcionPartido> cps = cpService.masVotadosAutonomico(cod);
        model.addAttribute("cps", cps);
        model.addAttribute("ruta", "/autonomicas/cp/mayorias/" + cod);
        model.addAttribute("tipo", "autonomicas");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    //Grupo de Monográficos por partido
    @GetMapping("/partido/{codigo}/autonomias/orderByCodAuto")
    public ResponseEntity<Dummy> verTodoDePartidoOrderAuto(@PathVariable("codigo") String cod, Model model) {
        List<CircunscripcionPartido> cps = cpService.findByIdPartidoAutonomiasCod(cod);
        model.addAttribute("cps", cps);
        model.addAttribute("ruta", "/autonomicas/cp/partido/" + cod + "/autonomias/orderByCodAuto");
        model.addAttribute("tipo", "autonomicas");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/partido/{codigo}/autonomias/orderByEscanios")
    public ResponseEntity<Dummy> verTodoDePartidoOrderEscanios(@PathVariable("codigo") String cod, Model model) {
        List<CircunscripcionPartido> cps = cpService.findByIdPartidoAutonomiasEscanios(cod);
        model.addAttribute("cps", cps);
        model.addAttribute("ruta", "/autonomicas/cp/partido/" + cod + "/autonomias/orderByEscanios");
        model.addAttribute("tipo", "autonomicas");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/partido/{codigo}/provincias")
    public ResponseEntity<Dummy> verTodoDePartidoPorProvincias(@PathVariable("codigo") String cod, Model model) {
        List<CircunscripcionPartido> cps = cpService.findByIdPartidoProvincias(cod);
        model.addAttribute("cps", cps);
        model.addAttribute("ruta", "/autonomicas/cp/partido/" + cod + "/provincias");
        model.addAttribute("tipo", "autonomicas");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }


    public void suscribeCircunscripciones() {
        if (!isSuscribed.get()) {
            System.out.println("Suscribiendo cp autonomicos...");
            isSuscribed.set(true);
            ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
            exec.scheduleAtFixedRate(() -> {
                if (circunscripcionPartidos.isEmpty()) {
                    System.out.println("Cargando circunscripciones/partido autonomicos");
                    circunscripcionPartidos = cpService.findAll();
                } else {
                    //System.out.println("Comprobando cambios cp autonomicos");
                    List<CircunscripcionPartido> circunscripcionesNew = null;
                    circunscripcionesNew = cpService.findAll();
                    if (!circunscripcionesNew.equals(circunscripcionPartidos)) {
                        System.out.println("Cambios detectados");
                        //TODO(Hacer el código necesario para ver que se hace con estos cambios)
                        getChanges(circunscripcionPartidos, circunscripcionesNew);
                        System.out.println(changes);
                        circunscripcionPartidos = circunscripcionesNew;
                    }
                }
            }, 0, 3, TimeUnit.SECONDS);
        }
    }


    private List<CircunscripcionPartido> getChanges(List<CircunscripcionPartido> oldList, List<CircunscripcionPartido> newList) {
        //System.out.println(differences);
        return newList.stream()
                .filter(element -> !oldList.contains(element))
                .toList();
    }
}
