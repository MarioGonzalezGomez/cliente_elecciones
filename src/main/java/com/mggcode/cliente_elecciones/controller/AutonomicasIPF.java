package com.mggcode.cliente_elecciones.controller;

import com.mggcode.cliente_elecciones.conexion.ConexionIPF;
import com.mggcode.cliente_elecciones.conexion.ConexionManager;
import com.mggcode.cliente_elecciones.config.Config;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


//@Controller
@RequestMapping("/autonomicas")
public class AutonomicasIPF {

    private final ConexionManager conexionManager = ConexionManager.getConexionManager();

    //TODO(Indicar aquí la dirección de la conexión IPF que quiero, ¿Cada controller va a un IPF?)

    private final ConexionIPF c = conexionManager.getConexionByAdress("localhost");
    private final Config conf = Config.getConfiguracion();


    @GetMapping("/ejemploIPF/entra")
    public String entra(Model model) {
        String bd = Config.config.getProperty("BDAutonomicas");
        c.enviarMensaje("itemset('" + bd + "MAPA/ENTRA','EVENT_RUN');");
        return "index";
    }

    @GetMapping("/ejemploIPF/sale")
    public String sale(Model model) {
        String bd = Config.config.getProperty("BDAutonomicas");
        c.enviarMensaje("itemset('" + bd + "MAPA/SALE','EVENT_RUN');");
        return "index";
    }
}
