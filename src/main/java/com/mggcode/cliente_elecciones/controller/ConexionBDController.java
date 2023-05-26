package com.mggcode.cliente_elecciones.controller;


import com.mggcode.cliente_elecciones.model.Dummy;
import com.mggcode.cliente_elecciones.service.ConexionBDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/bd")
public class ConexionBDController {

    @Autowired
    ConexionBDService service;

    @RequestMapping(path = "/principal")
    public ResponseEntity<Dummy> conectPrincipalDB() {
        service.conectPrincipalBD();
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @RequestMapping(path = "/reserva")
    public ResponseEntity<Dummy> conectReservaDB() {
        service.conectReservaBD();
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @RequestMapping(path = "/local")
    public ResponseEntity<Dummy> conectLocalDB() {
        service.conectLocalBD();
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

}
