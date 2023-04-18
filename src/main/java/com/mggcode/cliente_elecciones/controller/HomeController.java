package com.mggcode.cliente_elecciones.controller;

import com.mggcode.cliente_elecciones.config.Config;
import com.mggcode.cliente_elecciones.controller.autonomicas.ACPController;
import com.mggcode.cliente_elecciones.controller.autonomicas.ACircunscripcionController;
import com.mggcode.cliente_elecciones.controller.municipales.CPController;
import com.mggcode.cliente_elecciones.controller.municipales.CircunscripcionController;
import com.mggcode.cliente_elecciones.data.Data;
import com.mggcode.cliente_elecciones.utils.IPFMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.ConnectException;

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

    IPFMessageBuilder ipfMessageBuilder = IPFMessageBuilder.getInstance();

    @RequestMapping(value = "/")
    public String index(Model model) {
        if (Config.checkConnectionWithRetry()) {
            System.out.println("Servidor conectado: " + Config.connectedServer);
            startListeners();
            return "index";
        }
        return "index";

    }

    @RequestMapping("/{codigo}")
    public ResponseEntity<String> selectAutonomia(@PathVariable("codigo") String codigo){
        Data.autonomiaSeleccionada = codigo;
        return new ResponseEntity<>(codigo, HttpStatus.OK);
    }


    private void startListeners() {
        try {
            aCircunscripcionController.suscribeCircunscripciones();
            circunscripcionController.suscribeCircunscripciones();

            //TODO(esto es opcional y se puede activar en la parte del cliente como una opción)
            /*cpController.suscribeCircunscripciones();
            aCPController.suscribeCircunscripciones();*/

        } catch (ConnectException e) {
            System.err.println("Error de conexión");
        }
    }
}
