package com.mggcode.cliente_elecciones.controller;


import com.mggcode.cliente_elecciones.service.ConexionBDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/bd")
public class ConexionBDController {

    @Autowired
    ConexionBDService service;

    @RequestMapping(path = "/principal")
    public String conectPrincipalDB() {
        service.conectPrincipalBD();
        return "redirect:";
    }

    @RequestMapping(path = "/reserva")
    public String conectReservaDB() {
        service.conectReservaBD();
        return "redirect";
    }

    @RequestMapping(path = "/local")
    public String conectLocalDB() {
        service.conectLocalBD();
        return "redirect";
    }

}
