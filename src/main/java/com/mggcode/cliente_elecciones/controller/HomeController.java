package com.mggcode.cliente_elecciones.controller;

import com.mggcode.cliente_elecciones.controller.autonomicas.ACircunscripcionController;
import com.mggcode.cliente_elecciones.controller.municipales.CircunscripcionController;
import com.mggcode.cliente_elecciones.service.autonomicas.ACircunscripcionService;
import com.mggcode.cliente_elecciones.service.municipales.CircunscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {



    @Autowired
    ACircunscripcionController aCircunscripcionController;

    @Autowired
    CircunscripcionController circunscripcionController;


    @RequestMapping(value = "/")
    public String index(Model model) {
        aCircunscripcionController.suscribeCircunscripciones();
        circunscripcionController.suscribeCircunscripciones();
        return "index";
    }
}
