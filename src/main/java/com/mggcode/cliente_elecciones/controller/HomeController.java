package com.mggcode.cliente_elecciones.controller;

import com.mggcode.cliente_elecciones.DTO.CarmenDTO;
import com.mggcode.cliente_elecciones.config.Config;
import com.mggcode.cliente_elecciones.controller.autonomicas.ACPController;
import com.mggcode.cliente_elecciones.controller.autonomicas.ACircunscripcionController;
import com.mggcode.cliente_elecciones.controller.municipales.CPController;
import com.mggcode.cliente_elecciones.controller.municipales.CircunscripcionController;
import com.mggcode.cliente_elecciones.data.Data;
import com.mggcode.cliente_elecciones.model.Dummy;
import com.mggcode.cliente_elecciones.utils.CarmenDtoReader;
import com.mggcode.cliente_elecciones.utils.IPFFaldonesMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class HomeController {


    @Autowired
    ACircunscripcionController aCircunscripcionController;

    @Autowired
    CircunscripcionController circunscripcionController;

    @Autowired
    CPController cpController;
    @Autowired
    ACPController aCPController;

    IPFFaldonesMessageBuilder ipfMessageBuilder = IPFFaldonesMessageBuilder.getInstance();

    @RequestMapping(value = "/")
    public ResponseEntity<Dummy> index(Model model) {
        //if (Config.checkConnectionWithRetry()) {
        // System.out.println("Servidor conectado: " + Config.connectedServer);
        // startListeners();
        // runClient();
        //    return "index";
        // }
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);

    }

    public void startListeners() {
        aCircunscripcionController.suscribeCircunscripciones();
        circunscripcionController.suscribeCircunscripciones();

        //TODO(esto es opcional y se puede activar en la parte del cliente como una opción)

    }

    @RequestMapping("init/listeners")
    public ResponseEntity<Dummy> initListeners(Model model) {
        if (Config.checkConnectionWithRetry()) {
            startListeners();
        }
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @RequestMapping("/selected/{codigo}")
    public ResponseEntity<String> selectAutonomia(@PathVariable("codigo") String codigo) {
        System.out.println("---" + codigo);
        Data data = Data.getInstance();
        data.setAutonomiaSeleccionada(codigo);
        return new ResponseEntity<>(codigo, HttpStatus.OK);
    }


    @RequestMapping("/testExcel/{tipoElecciones}")
    ResponseEntity<CarmenDTO> testExcel(@PathVariable("tipoElecciones") int tipoElecciones) {
        CarmenDtoReader carmenDtoReader = CarmenDtoReader.getInstance();
        var res = carmenDtoReader.readExcel(tipoElecciones);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @RequestMapping("/testCsv/{tipoElecciones}")
    ResponseEntity<CarmenDTO> testCsv(@PathVariable("tipoElecciones") int tipoElecciones) {
        CarmenDtoReader.getInstance().readCsv(tipoElecciones);
        return null;
    }
}
