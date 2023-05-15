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

    @GetMapping("/reset")
    public void resetIPF() {
        c.enviarMensaje(ipfBuilder.resetIPF());
        c.enviarMensaje(ipfBuilderCartones.resetIPF());
    }

    @GetMapping("/carmen/lateral/entra")
    public void entraFaldonLateral() {
        c.enviarMensaje(ipfBuilder.lateralEntra());

    }

    @GetMapping("/carmen/lateral/{codigo}/despliega")
    public void despliegaFaldonLateral(@PathVariable("codigo") String codCircunscripcion) {
        c.enviarMensaje(ipfBuilder.lateralDespliega(codCircunscripcion));

    }

    @GetMapping("/carmen/lateral/{codigo}/repliega")
    public void repliegaFaldonLateral(@PathVariable("codigo") String codCircunscripcion) {
        c.enviarMensaje(ipfBuilder.lateralRepliega(codCircunscripcion));

    }

    @GetMapping("/carmen/lateral/{codigo}/actualiza")
    public void actualizaFaldonLateral(@PathVariable("codigo") String codCircunscripcion) {
        c.enviarMensaje(ipfBuilder.lateralActualiza(codCircunscripcion));
    }

    @GetMapping("/carmen/lateral/actualiza")
    public void actualizaFaldonLateral() {
        c.enviarMensaje(ipfBuilder.lateralActualiza());
    }

    @GetMapping("/carmen/lateral/sale")
    public void saleFaldonLateral() {
        c.enviarMensaje(ipfBuilder.lateralSale());
    }

    @GetMapping("/carmen/faldon/entra")
    public void faldonEntraAuto() {
        c.enviarMensaje(ipfBuilder.faldonAutoEntra());
    }

    @GetMapping("/carmen/faldon/sale")
    public void faldonSaleAuto() {
        c.enviarMensaje(ipfBuilder.faldonAutoSale());
    }

    @GetMapping("/carmen/faldon/actualiza")
    public void faldonAutoActualizo() {
        c.enviarMensaje(ipfBuilder.faldonAutoActualizo());
    }

    @GetMapping("/carmen/faldon/encadena")
    public void faldonAutoEncadena() {
        c.enviarMensaje(ipfBuilder.faldonAutoEncadena());
    }

    @GetMapping("/carmen/faldon/deAutoaMuni")
    public void faldonDeMuniaAuto() {
        c.enviarMensaje(ipfBuilder.deAutoAMuni());
    }

    //ARCOS
    @GetMapping("/arco/load")
    public void load() {
        c.enviarMensaje(ipfBuilderCartones.load());

    }

    @GetMapping("/arco/oficial/{circunscripcion}/{partido}/entraIzq")
    public void entraPartidoIzq1(@PathVariable("circunscripcion") String cir, @PathVariable("partido") String par) {
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

    }

    @GetMapping("/arco/principales/{circunscripcion}/{partido}/entraIzq")
    public void entraPartidoIzq2(@PathVariable("circunscripcion") String cir, @PathVariable("partido") String par) {
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


    }

    @GetMapping("/arco/desde_hasta/{circunscripcion}/{partido}/entraIzq")
    public void entraPartidoIzq3(@PathVariable("circunscripcion") String cir, @PathVariable("partido") String par) {
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

    }


    @GetMapping("/arco/oficial/{circunscripcion}/{partido}/entraDer")
    public void entraPartidoDer1(@PathVariable("circunscripcion") String cir, @PathVariable("partido") String par) {
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

    }

    @GetMapping("/arco/principales/{circunscripcion}/{partido}/entraDer")
    public void entraPartidoDer2(@PathVariable("circunscripcion") String cir, @PathVariable("partido") String par) {
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


    }

    @GetMapping("/arco/desde_hasta/{circunscripcion}/{partido}/entraDer")
    public void entraPartidoDer3(@PathVariable("circunscripcion") String cir, @PathVariable("partido") String par) {
        CarmenDTO carmenDTO = CarmenDtoReader.getInstance().readCarmenDto(cir);
        List<CircunscripcionPartido> cp = carmenDTO.getCpDTO()
                .stream().map(
                        c -> CircunscripcionPartido.mapFromCpDTO(carmenDTO.getCircunscripcion(), c))
                .filter(x -> x.getEscanos_hasta() > 0.0)
                .toList();
        CircunscripcionPartido seleccionado = cpSer.findById(cir, par);

        System.out.println(ipfBuilderCartones.partidoEntraDer(cp, seleccionado, 3));
        //c.enviarMensaje(ipfBuilder.lateralEntra());

    }

    @GetMapping("/arco/hasta/{circunscripcion}/{partido}/entraDer")
    public void entraPartidoDer4(@PathVariable("circunscripcion") String cir, @PathVariable("partido") String par) {
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

    }

    @GetMapping("/arco/reset")
    public void resetArco() {
        ipfBuilderCartones.reset();
        System.out.println("Reset completado");
        //c.enviarMensaje(mensajeXdeReset);

    }

    @GetMapping("/arco/entra")
    public void arcoEntra() {
        c.enviarMensaje(ipfBuilderCartones.arcoEntra());
    }

    @GetMapping("/arco/sale")
    public void arcoSale() {
        c.enviarMensaje(ipfBuilderCartones.arcoSale());
    }

    @GetMapping("/arco/pactos")
    public void arcoPactos() {
        c.enviarMensaje(ipfBuilderCartones.arcoPactos());
    }

    @GetMapping("/arco/sondeo/entra")
    public void arcoSondeoEntra() {
        c.enviarMensaje(ipfBuilderCartones.arcoSondeoEntra());
    }

    @GetMapping("/arco/sondeo/sale")
    public void arcoSondeoSale() {
        c.enviarMensaje(ipfBuilderCartones.arcoSondeoSale());
    }

    @GetMapping("/arco/sondeo/pactos")
    public void arcoSondeoPactos() {
        c.enviarMensaje(ipfBuilderCartones.arcoSondeoPactos());
    }

    //PARTICIPACION
    @GetMapping("/participacion/entra")
    public void participacionEntra() {
        c.enviarMensaje(ipfBuilderCartones.participacionEntra());
    }

    @GetMapping("/participacion/sale")
    public void participacionSale() {
        c.enviarMensaje(ipfBuilderCartones.participacionSale());
    }

    @GetMapping("/participacion/cambia")
    public void participacionCambia() {
        c.enviarMensaje(ipfBuilderCartones.participacionCambiaAuto());
    }

    //RESULTADOS

    @GetMapping("/resultados/entra")
    public void resultadosEntra() {
        c.enviarMensaje(ipfBuilderCartones.resultadosEntra());
    }

    @GetMapping("/resultados/sale")
    public void resultadosSale() {
        c.enviarMensaje(ipfBuilderCartones.resultadosSale());
    }

    @GetMapping("/resultados/cambia")
    public void resultadosCambia() {
        c.enviarMensaje(ipfBuilderCartones.resultadosCambiaAuto());
    }

    @GetMapping("/resultados/sondeo/entra")
    public void resultadosSondeoEntra() {
        c.enviarMensaje(ipfBuilderCartones.resultadosSondeoEntra());
    }

    @GetMapping("/resultados/sondeo/sale")
    public void resultadosSondeoSale() {
        c.enviarMensaje(ipfBuilderCartones.resultadosSondeoSale());
    }

    @GetMapping("/resultados/sondeo/cambia")
    public void resultadosSondeoCambia() {
        c.enviarMensaje(ipfBuilderCartones.resultadosSondeoCambiaAuto());
    }


}
