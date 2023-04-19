package com.mggcode.cliente_elecciones.controller.municipales;

import com.mggcode.cliente_elecciones.model.CircunscripcionPartido;
import com.mggcode.cliente_elecciones.service.municipales.CircunscripcionPartidoService;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequestMapping("/municipales/cp")
public class CPController {
    @Autowired
    private CircunscripcionPartidoService cpService;

    List<CircunscripcionPartido> circunscripcionPartidos = new ArrayList<>();
    AtomicBoolean isSuscribed = new AtomicBoolean(false);
    List<CircunscripcionPartido> changes;

    @GetMapping
    public String verCPS(Model model) {
        List<CircunscripcionPartido> cps = cpService.findAll();
        model.addAttribute("cps", cps);
        model.addAttribute("ruta", "/municipales/cp");
        model.addAttribute("tipo", "municipales");
        return "cps";
    }

    @RequestMapping(path = "/csv")
    public String findAllInCsv(RedirectAttributes redirectAttributes) throws IOException {
        cpService.findAllInCsv();
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        return "redirect:/municipales/cp";
    }

    @RequestMapping(path = "/excel")
    public String findAllInExcel(RedirectAttributes redirectAttributes) throws IOException {
        cpService.findAllInExcel();
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        return "redirect:/municipales/cp";
    }

    @GetMapping("/{codigoCir}/{codigoPar}")
    public String verCircunscripcionDetalle(@PathVariable("codigoCir") String codCir, @PathVariable("codigoPar") String codPar, Model model, @RequestHeader("Referer") String referer) {
        CircunscripcionPartido cp = cpService.findById(codCir, codPar);
        model.addAttribute("cp", cp);
        model.addAttribute("referer", referer);
        return "cpDetalle";
    }

    //Todos los partidos dentro de una circunscripción
    @GetMapping("/cp/circunscripcion/{codigo}")
    public String verTodosCPDeUnaCircunscripcion(@PathVariable("codigo") String cod, Model model) {
        List<CircunscripcionPartido> cps = cpService.findByIdCircunscripcion(cod);
        model.addAttribute("cps", cps);
        model.addAttribute("ruta", "/municipales/cp/circunscripcion/" + cod);
        model.addAttribute("tipo", "municipales");
        return "cps";
    }

    //Grupo de Mayorías (A nivel Nacional y Autonómico)

    @GetMapping("/mayorias/autonomias")
    public String verCPSporMA(Model model) {
        List<CircunscripcionPartido> cps = cpService.masVotadosPorAutonomia();
        model.addAttribute("cps", cps);
        model.addAttribute("ruta", "/municipales/cp/mayorias/autonomias");
        model.addAttribute("tipo", "municipales");
        return "cps";
    }

    @GetMapping("/mayorias/provincias")
    public String verCPSporMP(Model model) {
        List<CircunscripcionPartido> cps = cpService.masVotadosPorProvincia();
        model.addAttribute("cps", cps);
        model.addAttribute("ruta", "/municipales/cp/mayorias/provincias");
        model.addAttribute("tipo", "municipales");
        return "cps";
    }

    @GetMapping("/mayorias/{codigo}")
    public String verMayoriasAutonomicas(@PathVariable("codigo") String cod, Model model) {
        List<CircunscripcionPartido> cps = cpService.masVotadosAutonomico(cod);
        model.addAttribute("cps", cps);
        model.addAttribute("ruta", "/municipales/cp/mayorias/" + cod);
        model.addAttribute("tipo", "municipales");
        return "cps";
    }

    //Grupo de Monográficos por partido
    @GetMapping("/partido/{codigo}/autonomias/orderByCodAuto")
    public String verTodoDePartidoOrderAuto(@PathVariable("codigo") String cod, Model model) {
        List<CircunscripcionPartido> cps = cpService.findByIdPartidoAutonomiasCod(cod);
        model.addAttribute("cps", cps);
        model.addAttribute("ruta", "/municipales/cp/partido/" + cod + "/autonomias/orderByCodAuto");
        model.addAttribute("tipo", "municipales");
        return "cps";
    }

    @GetMapping("/partido/{codigo}/autonomias/orderByEscanios")
    public String verTodoDePartidoOrderEscanios(@PathVariable("codigo") String cod, Model model) {
        List<CircunscripcionPartido> cps = cpService.findByIdPartidoAutonomiasEscanios(cod);
        model.addAttribute("cps", cps);
        model.addAttribute("ruta", "/municipales/cp/partido/" + cod + "/autonomias/orderByEscanios");
        model.addAttribute("tipo", "municipales");
        return "cps";
    }

    @GetMapping("/partido/{codigo}/provincias")
    public String verTodoDePartidoPorProvincias(@PathVariable("codigo") String cod, Model model) {
        List<CircunscripcionPartido> cps = cpService.findByIdPartidoProvincias(cod);
        model.addAttribute("cps", cps);
        model.addAttribute("ruta", "/municipales/cp/partido/" + cod + "/provincias");
        model.addAttribute("tipo", "municipales");
        return "cps";
    }

    public void suscribeCircunscripciones() {
        if (!isSuscribed.get()) {
            System.out.println("Suscribiendo cp municipales...");
            isSuscribed.set(true);
            ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
            exec.scheduleAtFixedRate(() -> {
                if (circunscripcionPartidos.isEmpty()) {
                    System.out.println("Cargando circunscripciones/partido municipales");
                    circunscripcionPartidos = cpService.findAll();
                } else {
                    System.out.println("Comprobando cambios cp municipales");
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

        return newList.stream()
                .filter(element -> !oldList.contains(element))
                .toList();
    }

}
