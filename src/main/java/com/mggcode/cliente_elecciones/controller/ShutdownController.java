package com.mggcode.cliente_elecciones.controller;

import com.mggcode.cliente_elecciones.ClienteEleccionesApplication;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ShutdownController implements ApplicationContextAware {
    @Autowired
    RestTemplate restTemplate;
    private ApplicationContext context;

    @PostMapping("/shutdownContext")
    public void shutdownContext() {
        ClienteEleccionesApplication.closeCheck = false;
        ((ConfigurableApplicationContext) context).close();
    }


    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.context = ctx;
    }
}
