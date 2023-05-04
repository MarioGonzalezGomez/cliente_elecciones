package com.mggcode.cliente_elecciones.controller.autonomicas;

import com.mggcode.cliente_elecciones.model.Literal;
import com.mggcode.cliente_elecciones.model.Partido;
import com.mggcode.cliente_elecciones.service.autonomicas.ALiteralService;
import com.mggcode.cliente_elecciones.service.autonomicas.APartidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/autonomicas/literales")
public class ALiteralController {

    @Autowired
    private ALiteralService literalService;

    @GetMapping
    public String verLiterales(Model model) {
        List<Literal> literales = literalService.findAll();
        model.addAttribute("literales", literales);
        model.addAttribute("ruta", "/autonomicas/literales");
        return "literales";
    }
}
