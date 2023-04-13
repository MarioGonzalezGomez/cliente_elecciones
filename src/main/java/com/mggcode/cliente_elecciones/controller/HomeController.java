package com.mggcode.cliente_elecciones.controller;

import com.mggcode.cliente_elecciones.config.Config;
import com.mggcode.cliente_elecciones.controller.autonomicas.ACPController;
import com.mggcode.cliente_elecciones.controller.autonomicas.ACircunscripcionController;
import com.mggcode.cliente_elecciones.controller.municipales.CPController;
import com.mggcode.cliente_elecciones.controller.municipales.CircunscripcionController;
import com.mggcode.cliente_elecciones.exception.ConnectionException;
import com.mggcode.cliente_elecciones.service.autonomicas.ACircunscripcionService;
import com.mggcode.cliente_elecciones.service.municipales.CircunscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Controller
public class HomeController {


    @Autowired
    ACircunscripcionController aCircunscripcionController;

    @Autowired
    CircunscripcionController circunscripcionController;

    @Autowired
    CPController cpController;
    @Autowired
    ACPController aCPController;


    @RequestMapping(value = "/")
    public String index(Model model) {

        if (Config.checkConnectionWithRetry()) {
            System.out.println("Servidor conectado: " + Config.connectedServer);
            startListeners();
            return "index";
        }
        return "index";

    }


    private void startListeners() {
        try {
            aCircunscripcionController.suscribeCircunscripciones();
            circunscripcionController.suscribeCircunscripciones();
            cpController.suscribeCircunscripciones();
            aCPController.suscribeCircunscripciones();
        } catch (ConnectException e) {
            System.err.println("Error de conexión");
        }
    }
}
