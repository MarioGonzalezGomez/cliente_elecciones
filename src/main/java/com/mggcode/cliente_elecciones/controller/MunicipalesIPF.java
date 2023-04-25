package com.mggcode.cliente_elecciones.controller;

import com.mggcode.cliente_elecciones.conexion.ConexionIPF;
import com.mggcode.cliente_elecciones.conexion.ConexionManager;
import com.mggcode.cliente_elecciones.config.Config;
import com.mggcode.cliente_elecciones.utils.IPFFaldonesMessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/municipales")
public class MunicipalesIPF {
    private ConexionManager conexionManager;
    private final IPFFaldonesMessageBuilder ipfBuilder;
    private final Config conf;
    private final ConexionIPF c;

    public MunicipalesIPF() {
        conf = Config.getConfiguracion();
        conexionManager = ConexionManager.getConexionManager();
        ipfBuilder = IPFFaldonesMessageBuilder.getInstance();
        c = conexionManager.getConexionByAdress(Config.config.getProperty("direccion1"));
    }

    @GetMapping("/carmen/{codigo}/entra")
    public String entraFaldonLateral(@PathVariable("codigo") String codCircunscripcion, Model model) {
        System.out.println(ipfBuilder.lateralEntra());
        c.enviarMensaje(ipfBuilder.lateralEntra());
        return "redirect:/municipales/carmen/" + codCircunscripcion;
    }

    @GetMapping("/carmen/{codigo}/actualiza")
    public String actualizaFaldonLateral(@PathVariable("codigo") String codCircunscripcion, Model model) {
        System.out.println(ipfBuilder.lateralActualiza());
        c.enviarMensaje(ipfBuilder.lateralActualiza());
        return "redirect:/municipales/carmen/" + codCircunscripcion;
    }

    @GetMapping("/carmen/{codigo}/sale")
    public String saleFaldonLateral(@PathVariable("codigo") String codCircunscripcion, Model model) {
        System.out.println(ipfBuilder.lateralSale());
        c.enviarMensaje(ipfBuilder.lateralSale());
        return "redirect:/municipales/carmen/" + codCircunscripcion;
    }
}
