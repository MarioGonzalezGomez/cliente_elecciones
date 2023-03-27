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
        return "cps";
    }

    @GetMapping("/{codigoCir}/{codigoPar}")
    public String verCircunscripcionDetalle(@PathVariable("codigoCir") String codCir, @PathVariable("codigoPar") String codPar, Model model, @RequestHeader("Referer") String referer) {
        CircunscripcionPartido cp = cpService.findById(codCir, codPar);
        model.addAttribute("cp", cp);
        model.addAttribute("referer", referer);
        return "cpDetalle";
    }
}
