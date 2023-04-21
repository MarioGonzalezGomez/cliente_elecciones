package com.mggcode.cliente_elecciones.controller;

import com.mggcode.cliente_elecciones.conexion.ConexionIPF;
import com.mggcode.cliente_elecciones.conexion.ConexionManager;
import com.mggcode.cliente_elecciones.config.Config;
import com.mggcode.cliente_elecciones.utils.IPFMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/autonomicas")
public class AutonomicasIPF {
    private ConexionManager conexionManager;
    private final IPFMessageBuilder ipfBuilder;
    private final Config conf;
    private final ConexionIPF c;

    //TODO(Indicar aquí la dirección de la conexión IPF que quiero, ¿Cada controller va a un IPF?)
    public AutonomicasIPF() {
        conf = Config.getConfiguracion();
        conexionManager = ConexionManager.getConexionManager();
        ipfBuilder = IPFMessageBuilder.getInstance();
        c = conexionManager.getConexionByAdress("localhost");
    }

    @GetMapping("/carmen/{codigo}/entra")
    public String entraFaldonLateral(@PathVariable("codigo") String codCircunscripcion, Model model) {
        //System.out.println(ipfBuilder.lateralEntra());
        c.enviarMensaje(ipfBuilder.lateralEntra());
        return "redirect:/autonomicas/carmen/" + codCircunscripcion;
    }

    @GetMapping("/carmen/{codigo}/actualiza")
    public String actualizaFaldonLateral(@PathVariable("codigo") String codCircunscripcion, Model model) {
        //System.out.println(ipfBuilder.lateralActualiza());
        c.enviarMensaje(ipfBuilder.lateralActualiza());
        return "redirect:/autonomicas/carmen/" + codCircunscripcion;
    }

    @GetMapping("/carmen/{codigo}/sale")
    public String saleFaldonLateral(@PathVariable("codigo") String codCircunscripcion, Model model) {
        //System.out.println(ipfBuilder.lateralSale());
        c.enviarMensaje(ipfBuilder.lateralSale());
        return "redirect:/autonomicas/carmen/" + codCircunscripcion;
    }
}
