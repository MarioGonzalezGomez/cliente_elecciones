package com.mggcode.cliente_elecciones.controller.municipales;

import com.mggcode.cliente_elecciones.model.Dummy;
import com.mggcode.cliente_elecciones.model.Literal;
import com.mggcode.cliente_elecciones.service.municipales.LiteralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/municipales/literales")
public class LiteralController {

    @Autowired
    private LiteralService literalService;

    @GetMapping
    public ResponseEntity<Dummy> verLiterales(Model model) {
        List<Literal> literales = literalService.findAll();
        model.addAttribute("literales", literales);
        model.addAttribute("ruta", "/municipales/literales");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }
}
