package com.mggcode.cliente_elecciones.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mggcode.cliente_elecciones.model.Configuracion;
import com.mggcode.cliente_elecciones.model.Dummy;
import com.mggcode.cliente_elecciones.service.ConfiguracionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/configuracion")
public class ConfiguracionController {

    @Autowired
    private ConfiguracionService configuracionService;

    @GetMapping()
    public ResponseEntity<Dummy> mostrarFormulario(Model model) {
        Configuracion configuracion = configuracionService.cargarConfiguracion();
        model.addAttribute("configuracion", configuracion);
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Dummy> guardarConfiguracion(@RequestBody String jsonConfiguracion) {
        Configuracion configuracion = convertirJsonAConfiguracion(jsonConfiguracion);
        System.out.println(jsonConfiguracion);
        configuracionService.guardarConfiguracion(configuracion);
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    private Configuracion convertirJsonAConfiguracion(String jsonConfiguracion) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonConfiguracion, Configuracion.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            // Manejar la excepción
        }
        return null;
    }
}

