package com.mggcode.cliente_elecciones.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mggcode.cliente_elecciones.model.Configuracion;
import com.mggcode.cliente_elecciones.service.ConfiguracionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/configuracion")
public class ConfiguracionController {

    @Autowired
    private ConfiguracionService configuracionService;

    @GetMapping()
    public String mostrarFormulario(Model model) {
        Configuracion configuracion = configuracionService.cargarConfiguracion();
        model.addAttribute("configuracion", configuracion);
        return "configuraciones";
    }

    @PostMapping()
    public String guardarConfiguracion(@RequestBody String jsonConfiguracion) {
        Configuracion configuracion = convertirJsonAConfiguracion(jsonConfiguracion);
        configuracionService.guardarConfiguracion(configuracion);
        return "redirect:/configuracion";
    }

    private Configuracion convertirJsonAConfiguracion(String jsonConfiguracion) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonConfiguracion, Configuracion.class);
        } catch (IOException e) {
            // Manejar la excepción
        }
        return null;
    }
}

