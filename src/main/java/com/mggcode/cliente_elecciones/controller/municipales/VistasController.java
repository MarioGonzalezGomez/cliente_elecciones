package com.mggcode.cliente_elecciones.controller.municipales;

import com.mggcode.cliente_elecciones.model.CircunscripcionPartido;
import com.mggcode.cliente_elecciones.model.Partido;
import com.mggcode.cliente_elecciones.service.municipales.CircunscripcionPartidoService;
import com.mggcode.cliente_elecciones.service.municipales.PartidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/municipales")
public class VistasController {

    @Autowired
    private CircunscripcionPartidoService cpService;

    @Autowired
    private PartidoService partidoService;

    // @GetMapping("/circunscripciones/vista")
    // public String verCircunscripciones(Model model) {
    //     List<Circunscripcion> circunscripciones = aCircunscripcionController.findAll().getBody();
    //     model.addAttribute("circunscripciones", circunscripciones);
    //     model.addAttribute("rutaCSV", "/municipales/circunscripciones/csv");
    //     model.addAttribute("rutaExcel", "/municipales/circunscripciones/excel");
    //     return "circunscripciones";
    // }
//
    @GetMapping("/partidos/vista")
    public String verPartidos(Model model) throws IOException {
        List<Partido> partidos = partidoService.findAll();
        model.addAttribute("partidos", partidos);
        model.addAttribute("rutaCSV", "/municipales/partidos/csv");
        model.addAttribute("rutaExcel", "/municipales/partidos/excel");
        return "partidos";
    }

    @GetMapping("/cp/vista")
    public String verCPS(Model model) throws IOException {
        List<CircunscripcionPartido> cps = cpService.findAll();
        model.addAttribute("cps", cps);
        model.addAttribute("rutaCSV", "/municipales/cp/csv");
        model.addAttribute("rutaExcel", "/municipales/cp/excel");
        return "cps";
    }

    // @GetMapping("/cp/mayorias/autonomias/vista")
    // public String verCPSporMA(Model model) {
    //     List<CircunscripcionPartido> cps = aCPController.masVotadosPorAutonomia().getBody();
    //     model.addAttribute("cps", cps);
    //     model.addAttribute("rutaCSV", "/municipales/cp/mayorias/autonomias/csv");
    //     model.addAttribute("rutaExcel", "/municipales/cp/mayorias/autonomias/excel");
    //     return "cps";
    // }
//
    // @GetMapping("/cp/mayorias/provincias/vista")
    // public String verCPSporMP(Model model) {
    //     List<CircunscripcionPartido> cps = aCPController.masVotadosPorProvincia().getBody();
    //     model.addAttribute("cps", cps);
    //     model.addAttribute("rutaCSV", "/municipales/cp/mayorias/provincias/csv");
    //     model.addAttribute("rutaExcel", "/municipales/cp/mayorias/provincias/excel");
    //     return "cps";
    // }
//
    // @GetMapping("/cp/mayorias/{codigo}/vista")
    // public String verMayoriasmunicipales(@PathVariable("codigo") String cod, Model model) {
    //     List<CircunscripcionPartido> cps = aCPController.masVotadosAutonomicoPorProvincia(cod).getBody();
    //     model.addAttribute("cps", cps);
    //     model.addAttribute("rutaCSV", "/municipales/cp/mayorias/" + cod + "/csv");
    //     model.addAttribute("rutaExcel", "/municipales/cp/mayorias/" + cod + "/excel");
    //     return "cps";
    // }
//
    // @GetMapping("/cp/circunscripcion/{codigo}/vista")
    // public String verTodosCPDeUnaCircunscripcion(@PathVariable("codigo") String cod, Model model) {
    //     List<CircunscripcionPartido> cps = aCPController.findByIdCircunscripcion(cod).getBody();
    //     model.addAttribute("cps", cps);
    //     model.addAttribute("rutaCSV", "/municipales/cp/circunscripcion/" + cod + "/csv");
    //     model.addAttribute("rutaExcel", "/municipales/cp/circunscripcion/" + cod + "/excel");
    //     return "cps";
    // }
//
    // @GetMapping("/cp/partido/{codigo}/autonomias/orderByCodAuto/vista")
    // public String verTodoDePartidoOrderAuto(@PathVariable("codigo") String cod, Model model) {
    //     List<CircunscripcionPartido> cps = aCPController.findByIdPartidoAutonomiasCod(cod).getBody();
    //     model.addAttribute("cps", cps);
    //     model.addAttribute("rutaCSV", "/municipales/cp/partido/" + cod + "/autonomias/orderByCodAuto/csv");
    //     model.addAttribute("rutaExcel", "/municipales/cp/partido/" + cod + "/autonomias/orderByCodAuto/excel");
    //     return "cps";
    // }
//
    // @GetMapping("/cp/partido/{codigo}/autonomias/orderByEscanios/vista")
    // public String verTodoDePartidoOrderEscanios(@PathVariable("codigo") String cod, Model model) {
    //     List<CircunscripcionPartido> cps = aCPController.findByIdPartidoAutonomiasEscanios(cod).getBody();
    //     model.addAttribute("cps", cps);
    //     model.addAttribute("rutaCSV", "/municipales/cp/partido/" + cod + "/autonomias/orderByEscanios/csv");
    //     model.addAttribute("rutaExcel", "/municipales/cp/partido/" + cod + "/autonomias/orderByEscanios/excel");
    //     return "cps";
    // }
//
    // @GetMapping("/cp/partido/{codigo}/provincias/vista")
    // public String verTodoDePartidoPorProvincias(@PathVariable("codigo") String cod, Model model) {
    //     List<CircunscripcionPartido> cps = aCPController.findByIdPartidoProvincias(cod).getBody();
    //     model.addAttribute("cps", cps);
    //     model.addAttribute("rutaCSV", "/municipales/cp/partido/" + cod + "/provincias/csv");
    //     model.addAttribute("rutaExcel", "/municipales/cp/partido/" + cod + "/provincias/excel");
    //     return "cps";
    // }


}
