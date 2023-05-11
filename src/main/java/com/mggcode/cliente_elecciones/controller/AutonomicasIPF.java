package com.mggcode.cliente_elecciones.controller;

import com.mggcode.cliente_elecciones.DTO.CarmenDTO;
import com.mggcode.cliente_elecciones.conexion.ConexionIPF;
import com.mggcode.cliente_elecciones.conexion.ConexionManager;
import com.mggcode.cliente_elecciones.config.Config;
import com.mggcode.cliente_elecciones.model.CircunscripcionPartido;
import com.mggcode.cliente_elecciones.service.autonomicas.ACircunscripcionPartidoService;
import com.mggcode.cliente_elecciones.utils.CarmenDtoReader;
import com.mggcode.cliente_elecciones.utils.IPFCartonesMessageBuilder;
import com.mggcode.cliente_elecciones.utils.IPFFaldonesMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;


@Controller
@RequestMapping("/autonomicas")
public class AutonomicasIPF {
    private ConexionManager conexionManager;
    private final IPFFaldonesMessageBuilder ipfBuilder;

    private final IPFCartonesMessageBuilder ipfBuilderCartones;
    private final Config conf;
    private final ConexionIPF c;
    @Autowired
    private ACircunscripcionPartidoService cpSer;


    //TODO(Indicar aquí la dirección de la conexión IPF que quiero, ¿Cada controller va a un IPF?)
    public AutonomicasIPF() {
        conf = Config.getConfiguracion();
        conexionManager = ConexionManager.getConexionManager();
        ipfBuilder = IPFFaldonesMessageBuilder.getInstance();
        ipfBuilderCartones = IPFCartonesMessageBuilder.getInstance();
        c = conexionManager.getConexionByAdress(Config.config.getProperty("direccion1"));
    }

    @GetMapping("/carmen/lateral/entra")
    public String entraFaldonLateral() {
        c.enviarMensaje(ipfBuilder.lateralEntra());
        return "ok";
    }

    @GetMapping("/carmen/lateral/{codigo}/despliega")
    public String despliegaFaldonLateral(@PathVariable("codigo") String codCircunscripcion) {
        c.enviarMensaje(ipfBuilder.lateralDespliega(codCircunscripcion));
        return "ok";
    }

    @GetMapping("/carmen/lateral/{codigo}/repliega")
    public String repliegaFaldonLateral(@PathVariable("codigo") String codCircunscripcion) {
        c.enviarMensaje(ipfBuilder.lateralRepliega(codCircunscripcion));
        return "ok";
    }

    @GetMapping("/carmen/lateral/{codigo}/actualiza")
    public String actualizaFaldonLateral(@PathVariable("codigo") String codCircunscripcion) {
        c.enviarMensaje(ipfBuilder.lateralActualiza(codCircunscripcion));
        return "ok";
    }

    @GetMapping("/carmen/lateral/actualiza")
    public String actualizaFaldonLateral() {
        c.enviarMensaje(ipfBuilder.lateralActualiza());
        return "ko";
    }

    @GetMapping("/carmen/lateral/sale")
    public String saleFaldonLateral() {
        c.enviarMensaje(ipfBuilder.lateralSale());
        return "ok";
    }

    @GetMapping("/carmen/faldon/entra")
    public String faldonEntraAuto() {
        c.enviarMensaje(ipfBuilder.faldonAutoEntra());
        return "ok";
    }

    @GetMapping("/carmen/faldon/sale")
    public String faldonSaleAuto() {
        c.enviarMensaje(ipfBuilder.faldonAutoEntra());
        return "ok";

    }

    @GetMapping("/arco/load")
    public String load() {
        c.enviarMensaje(ipfBuilderCartones.load());
        return "ok";
    }

    @GetMapping("/arco/oficial/{circunscripcion}/{partido}/entraIzq")
    public String entraPartidoIzq1(@PathVariable("circunscripcion") String cir, @PathVariable("partido") String par) {
        CarmenDTO carmenDTO = CarmenDtoReader.getInstance().readCarmenDto(cir);
        List<CircunscripcionPartido> cp = carmenDTO.getCpDTO()
                .stream().map(
                        c -> CircunscripcionPartido.mapFromCpDTO(carmenDTO.getCircunscripcion(), c))
                .filter(x -> x.getEscanos_hasta() > 0.0)
                .toList();

        CircunscripcionPartido seleccionado = cpSer.findById(cir, par);
        String resultado1 = ipfBuilderCartones.partidoEntraIzq(cp, seleccionado, 1);
        //System.out.println(resultado1);
        c.enviarMensaje(resultado1);
        return "ok";
    }

    @GetMapping("/arco/principales/{circunscripcion}/{partido}/entraIzq")
    public String entraPartidoIzq2(@PathVariable("circunscripcion") String cir, @PathVariable("partido") String par) {
        CarmenDTO carmenDTO = CarmenDtoReader.getInstance().readCarmenDto(cir);
        List<CircunscripcionPartido> cp = carmenDTO.getCpDTO()
                .stream().map(
                        c -> CircunscripcionPartido.mapFromCpDTO(carmenDTO.getCircunscripcion(), c))
                .filter(x -> x.getEscanos_hasta() > 0.0)
                .toList();
        CircunscripcionPartido seleccionado = cpSer.findById(cir, par);

        String resultado1 = ipfBuilderCartones.partidoEntraIzq(cp, seleccionado, 2);
        //System.out.println(resultado1);
        c.enviarMensaje(resultado1);

        return "ok";
    }

    @GetMapping("/arco/desde_hasta/{circunscripcion}/{partido}/entraIzq")
    public String entraPartidoIzq3(@PathVariable("circunscripcion") String cir, @PathVariable("partido") String par) {
        CarmenDTO carmenDTO = CarmenDtoReader.getInstance().readCarmenDto(cir);
        List<CircunscripcionPartido> cp = carmenDTO.getCpDTO()
                .stream().map(
                        c -> CircunscripcionPartido.mapFromCpDTO(carmenDTO.getCircunscripcion(), c))
                .filter(x -> x.getEscanos_hasta() > 0.0)
                .toList();
        CircunscripcionPartido seleccionado = cpSer.findById(cir, par);
        String resultado1 = ipfBuilderCartones.partidoEntraIzq(cp, seleccionado, 3);
        //  System.out.println(resultado1);
        c.enviarMensaje(resultado1);
        String resultado2 = ipfBuilderCartones.partidoEntraIzq(cp, seleccionado, 4);
        // System.out.println(resultado2);
        c.enviarMensaje(resultado2);
        return "ok";
    }


    @GetMapping("/arco/oficial/{circunscripcion}/{partido}/entraDer")
    public String entraPartidoDer1(@PathVariable("circunscripcion") String cir, @PathVariable("partido") String par) {
        CarmenDTO carmenDTO = CarmenDtoReader.getInstance().readCarmenDto(cir);
        List<CircunscripcionPartido> cp = carmenDTO.getCpDTO()
                .stream().map(
                        c -> CircunscripcionPartido.mapFromCpDTO(carmenDTO.getCircunscripcion(), c))
                .filter(x -> x.getEscanos_hasta() > 0.0)
                .toList();
        CircunscripcionPartido seleccionado = cpSer.findById(cir, par);

        String resultado1 = ipfBuilderCartones.partidoEntraDer(cp, seleccionado, 1);
        //System.out.println(resultado1);
        c.enviarMensaje(resultado1);
        return "ok";
    }

    @GetMapping("/arco/principales/{circunscripcion}/{partido}/entraDer")
    public String entraPartidoDer2(@PathVariable("circunscripcion") String cir, @PathVariable("partido") String par) {
        CarmenDTO carmenDTO = CarmenDtoReader.getInstance().readCarmenDto(cir);
        List<CircunscripcionPartido> cp = carmenDTO.getCpDTO()
                .stream().map(
                        c -> CircunscripcionPartido.mapFromCpDTO(carmenDTO.getCircunscripcion(), c))
                .filter(x -> x.getEscanos_hasta() > 0.0)
                .toList();
        CircunscripcionPartido seleccionado = cpSer.findById(cir, par);

        String resultado1 = ipfBuilderCartones.partidoEntraDer(cp, seleccionado, 2);
        //System.out.println(resultado1);
        c.enviarMensaje(resultado1);

        return "ok";
    }

    @GetMapping("/arco/desde_hasta/{circunscripcion}/{partido}/entraDer")
    public String entraPartidoDer3(@PathVariable("circunscripcion") String cir, @PathVariable("partido") String par) {
        CarmenDTO carmenDTO = CarmenDtoReader.getInstance().readCarmenDto(cir);
        List<CircunscripcionPartido> cp = carmenDTO.getCpDTO()
                .stream().map(
                        c -> CircunscripcionPartido.mapFromCpDTO(carmenDTO.getCircunscripcion(), c))
                .filter(x -> x.getEscanos_hasta() > 0.0)
                .toList();
        CircunscripcionPartido seleccionado = cpSer.findById(cir, par);

        System.out.println(ipfBuilderCartones.partidoEntraDer(cp, seleccionado, 3));
        //c.enviarMensaje(ipfBuilder.lateralEntra());
        return "ok";
    }

    @GetMapping("/arco/hasta/{circunscripcion}/{partido}/entraDer")
    public String entraPartidoDer4(@PathVariable("circunscripcion") String cir, @PathVariable("partido") String par) {
        CarmenDTO carmenDTO = CarmenDtoReader.getInstance().readCarmenDto(cir);
        List<CircunscripcionPartido> cp = carmenDTO.getCpDTO()
                .stream().map(
                        c -> CircunscripcionPartido.mapFromCpDTO(carmenDTO.getCircunscripcion(), c))
                .filter(x -> x.getEscanos_hasta() > 0.0)
                .toList();
        CircunscripcionPartido seleccionado = cpSer.findById(cir, par);

        System.out.println(ipfBuilderCartones.partidoEntraDer(cp, seleccionado, 4));
        //c.enviarMensaje(ipfBuilder.lateralEntra());
        String resultado1 = ipfBuilderCartones.partidoEntraDer(cp, seleccionado, 3);
        //  System.out.println(resultado1);
        c.enviarMensaje(resultado1);
        String resultado2 = ipfBuilderCartones.partidoEntraDer(cp, seleccionado, 4);
        // System.out.println(resultado2);
        c.enviarMensaje(resultado2);
        return "ok";
    }

    @GetMapping("/arco/reset")
    public String resetArco() {
        ipfBuilderCartones.reset();
        System.out.println("Reset completado");
        //c.enviarMensaje(mensajeXdeReset);
        return "ok";
    }
}
