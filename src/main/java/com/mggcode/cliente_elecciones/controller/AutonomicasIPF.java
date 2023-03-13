package com.mggcode.cliente_elecciones.controller;

import com.mggcode.cliente_elecciones.conexion.ConexionIPF;
import com.mggcode.cliente_elecciones.config.ConfigIPF;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


//@Controller
@RequestMapping("/autonomicas")
public class AutonomicasIPF {

    private final ConexionIPF c = ConexionIPF.getConexion();
    private final ConfigIPF conf = ConfigIPF.getConfiguracion();


    @GetMapping("/ejemploIPF/entra")
    public String entra(Model model) {
        String bd = ConfigIPF.config.getProperty("BDMunicipales");
        c.enviarMensaje("itemset('" + bd + "MAPA/ENTRA','EVENT_RUN');");
        return "index";
    }

    @GetMapping("/ejemploIPF/sale")
    public String sale(Model model) {
        String bd = ConfigIPF.config.getProperty("BDMunicipales");
        c.enviarMensaje("itemset('" + bd + "MAPA/SALE','EVENT_RUN');");
        return "index";
    }
}
