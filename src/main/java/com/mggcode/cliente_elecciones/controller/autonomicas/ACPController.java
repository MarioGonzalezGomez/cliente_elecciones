package com.mggcode.cliente_elecciones.controller.autonomicas;

import com.mggcode.cliente_elecciones.model.Circunscripcion;
import com.mggcode.cliente_elecciones.model.CircunscripcionPartido;
import com.mggcode.cliente_elecciones.service.autonomicas.ACircunscripcionPartidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/autonomicas/cp")
public class ACPController {
    @Autowired
    private ACircunscripcionPartidoService cpService;

    @GetMapping
    public String verCPS(Model model) {
        List<CircunscripcionPartido> cps = cpService.findAll();
        model.addAttribute("cps", cps);
        model.addAttribute("ruta", "/autonomicas/cp");
        return "cps";
    }

    @RequestMapping(path = "/csv")
    public String findAllInCsv(RedirectAttributes redirectAttributes) throws IOException {
        cpService.findAllInCsv();
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        return "redirect:/autonomicas/cp";
    }

    @RequestMapping(path = "/excel")
    public String findAllInExcel(RedirectAttributes redirectAttributes) throws IOException {
        cpService.findAllInExcel();
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        return "redirect:/autonomicas/cp";
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
        model.addAttribute("ruta", "/autonomicas/cp/circunscripcion/" + cod);
        return "cps";
    }

    //Grupo de Mayorías (A nivel Nacional y Autonómico)

    @GetMapping("/mayorias/autonomias")
    public String verCPSporMA(Model model) {
        List<CircunscripcionPartido> cps = cpService.masVotadosPorAutonomia();
        model.addAttribute("cps", cps);
        model.addAttribute("ruta", "/autonomicas/cp/mayorias/autonomias");
        return "cps";
    }

    @GetMapping("/mayorias/provincias")
    public String verCPSporMP(Model model) {
        List<CircunscripcionPartido> cps = cpService.masVotadosPorProvincia();
        model.addAttribute("cps", cps);
        model.addAttribute("ruta", "/autonomicas/cp/mayorias/provincias");
        return "cps";
    }

    @GetMapping("/mayorias/{codigo}")
    public String verMayoriasAutonomicas(@PathVariable("codigo") String cod, Model model) {
        List<CircunscripcionPartido> cps = cpService.masVotadosAutonomico(cod);
        model.addAttribute("cps", cps);
        model.addAttribute("ruta", "/autonomicas/cp/mayorias/" + cod);
        return "cps";
    }

    //Grupo de Monográficos por partido
    @GetMapping("/partido/{codigo}/autonomias/orderByCodAuto")
    public String verTodoDePartidoOrderAuto(@PathVariable("codigo") String cod, Model model) {
        List<CircunscripcionPartido> cps = cpService.findByIdPartidoAutonomiasCod(cod);
        model.addAttribute("cps", cps);
        model.addAttribute("ruta", "/autonomicas/cp/partido/" + cod + "/autonomias/orderByCodAuto");
        return "cps";
    }

    @GetMapping("/partido/{codigo}/autonomias/orderByEscanios")
    public String verTodoDePartidoOrderEscanios(@PathVariable("codigo") String cod, Model model) {
        List<CircunscripcionPartido> cps = cpService.findByIdPartidoAutonomiasEscanios(cod);
        model.addAttribute("cps", cps);
        model.addAttribute("ruta", "/autonomicas/cp/partido/" + cod + "/autonomias/orderByEscanios");
        return "cps";
    }

    @GetMapping("/partido/{codigo}/provincias")
    public String verTodoDePartidoPorProvincias(@PathVariable("codigo") String cod, Model model) {
        List<CircunscripcionPartido> cps = cpService.findByIdPartidoProvincias(cod);
        model.addAttribute("cps", cps);
        model.addAttribute("ruta", "/autonomicas/cp/partido/" + cod + "/provincias");
        return "cps";
    }

}
