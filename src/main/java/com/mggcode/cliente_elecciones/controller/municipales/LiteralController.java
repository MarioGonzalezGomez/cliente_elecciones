package com.mggcode.cliente_elecciones.controller.municipales;

import com.mggcode.cliente_elecciones.model.Literal;
import com.mggcode.cliente_elecciones.service.municipales.LiteralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/municipales/literales")
public class LiteralController {

    @Autowired
    private LiteralService literalService;

    @GetMapping
    public String verLiterales(Model model) {
        List<Literal> literales = literalService.findAll();
        model.addAttribute("literales", literales);
        model.addAttribute("ruta", "/municipales/literales");
        return "literales";
    }
}
