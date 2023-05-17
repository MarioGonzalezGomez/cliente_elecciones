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
    public String resetIPF() {
        c.enviarMensaje(ipfBuilder.resetIPF());
        c.enviarMensaje(ipfBuilderCartones.resetIPF());
        return "redirect:";

    }

    //LATERAL

    @GetMapping("/carmen/lateral/entra")
    public String entraFaldonLateral() {
        c.enviarMensaje(ipfBuilder.lateralEntra());
        return "redirect:";

    }

    @GetMapping("/carmen/lateral/{codigo}/despliega")
    public String despliegaFaldonLateral(@PathVariable("codigo") String codCircunscripcion) {
        c.enviarMensaje(ipfBuilder.lateralDespliega(codCircunscripcion));
        return "redirect:";

    }

    @GetMapping("/carmen/lateral/{codigo}/repliega")
    public String repliegaFaldonLateral(@PathVariable("codigo") String codCircunscripcion) {
        c.enviarMensaje(ipfBuilder.lateralRepliega(codCircunscripcion));
        return "redirect:";

    }

    @GetMapping("/carmen/lateral/{codigo}/actualiza")
    public String actualizaFaldonLateral(@PathVariable("codigo") String codCircunscripcion) {
        c.enviarMensaje(ipfBuilder.lateralActualiza(codCircunscripcion));
        return "redirect:";
    }

    @GetMapping("/carmen/lateral/actualiza")
    public String actualizaFaldonLateral() {
        c.enviarMensaje(ipfBuilder.lateralActualiza());
        return "redirect:";
    }

    @GetMapping("/carmen/lateral/sale")
    public String saleFaldonLateral() {
        c.enviarMensaje(ipfBuilder.lateralSale());
        return "redirect:";
    }

    //INFERIOR

    @GetMapping("/carmen/faldon/entra")
    public String faldonEntraAuto() {
        c.enviarMensaje(ipfBuilder.faldonAutoEntra());
        return "redirect:";
    }

    @GetMapping("/carmen/faldon/sondeo/entra")
    public String faldonEntraAutoSondeo() {
        c.enviarMensaje(ipfBuilder.faldonAutoSondeoEntra());
        return "redirect:";
    }

    @GetMapping("/carmen/faldon/sale")
    public String faldonSaleAuto() {
        c.enviarMensaje(ipfBuilder.faldonAutoSale());
        return "redirect:";
    }

    @GetMapping("/carmen/faldon/sondeo/sale")
    public String faldonSaleAutoSondeo() {
        c.enviarMensaje(ipfBuilder.faldonAutoSondeoSale());
        return "redirect:";
    }

    @GetMapping("/carmen/faldon/actualiza")
    public String faldonAutoActualizo() {
        c.enviarMensaje(ipfBuilder.faldonAutoActualizo());
        return "redirect:";
    }

    @GetMapping("/carmen/faldon/encadena")
    public String faldonAutoEncadena() {
        c.enviarMensaje(ipfBuilder.faldonAutoEncadena());
        return "redirect:";
    }

    @GetMapping("/carmen/faldon/deAutoaMuni")
    public String faldonDeMuniaAuto() {
        c.enviarMensaje(ipfBuilder.deAutoAMuni());
        return "redirect:";
    }

    @GetMapping("/carmen/faldon/deAutoSondeoAMuniSondeo")
    public String deAutoSondeoAMuniSondeo() {
        c.enviarMensaje(ipfBuilder.deAutoSondeoAMuniSondeo());
        return "redirect:";
    }

    @GetMapping("/carmen/faldon/deAutoSondeoAMuni")
    public String deAutoSondeoAMuni() {
        c.enviarMensaje(ipfBuilder.deAutoSondeoAMuni());
        return "redirect:";
    }

    @GetMapping("/carmen/faldon/deAutoSondeoAAuto")
    public String deAutoSondeoAAuto() {
        c.enviarMensaje(ipfBuilder.deAutoSondeoAAuto());
        return "redirect:";
    }

    //SEDES

    @GetMapping("/carmen/sedes/entra")
    public String faldonSedesEntra() {
        c.enviarMensaje(ipfBuilder.sedesEntra());
        return "redirect:";
    }

    @GetMapping("/carmen/sedes/sale")
    public String faldonSedesSale() {
        c.enviarMensaje(ipfBuilder.sedesSale());
        return "redirect:";
    }

    //ARCOS
    @GetMapping("/arco/load")
    public String load() {
        c.enviarMensaje(ipfBuilderCartones.load());
        return "redirect:";

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
        return "redirect:";

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
        return "redirect:";


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
        return "redirect:";


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

        return "redirect:";

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
        return "redirect:";


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
        return "redirect:";


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
        return "redirect:";


    }

    @GetMapping("/arco/reset")
    public String resetArco() {
        ipfBuilderCartones.reset();
        System.out.println("Reset completado");
        return "redirect:";
        //c.enviarMensaje(mensajeXdeReset);

    }

    @GetMapping("/arco/entra")
    public String arcoEntra() {
        c.enviarMensaje(ipfBuilderCartones.arcoEntra());
        return "redirect:";
    }

    @GetMapping("/arco/entra/delay")
    public String arcoEntraDelay() {
        c.enviarMensaje(ipfBuilderCartones.arcoEntraDelay());
        return "redirect:";
    }

    @GetMapping("/arco/sale")
    public String arcoSale() {
        c.enviarMensaje(ipfBuilderCartones.arcoSale());
        return "redirect:";
    }

    @GetMapping("/arco/pactos")
    public String arcoPactos() {
        c.enviarMensaje(ipfBuilderCartones.arcoPactos());
        return "redirect:";
    }

    @GetMapping("/arco/sondeo/entra")
    public String arcoSondeoEntra() {
        c.enviarMensaje(ipfBuilderCartones.arcoSondeoEntra());
        return "redirect:";
    }

    @GetMapping("/arco/sondeo/entra/delay")
    public String arcoSondeoEntraDelayed() {
        c.enviarMensaje(ipfBuilderCartones.arcoSondeoEntraDelay());
        return "redirect:";
    }


    @GetMapping("/arco/sondeo/sale")
    public String arcoSondeoSale() {
        c.enviarMensaje(ipfBuilderCartones.arcoSondeoSale());
        return "redirect:";
    }

    @GetMapping("/arco/sondeo/pactos")
    public String arcoSondeoPactos() {
        c.enviarMensaje(ipfBuilderCartones.arcoSondeoPactos());
        return "redirect:";
    }

    //PARTICIPACION
    @GetMapping("/participacion/entra")
    public String participacionEntra() {
        c.enviarMensaje(ipfBuilderCartones.participacionEntra());
        return "redirect:";
    }


    @GetMapping("/participacion/entra/delay")
    public String participacionEntraDelay() {
        c.enviarMensaje(ipfBuilderCartones.participacionEntraDelay());
        return "redirect:";
    }


    @GetMapping("/participacion/sale")
    public String participacionSale() {
        c.enviarMensaje(ipfBuilderCartones.participacionSale());
        return "redirect:";
    }

    @GetMapping("/participacion/cambia")
    public String participacionCambia() {
        c.enviarMensaje(ipfBuilderCartones.participacionCambiaAuto());
        return "redirect:";
    }


    @GetMapping("/participacion/entra/esp")
    public String entraParticipacionEsp() {
        c.enviarMensaje(ipfBuilderCartones.participacionEspAuto());
        return "redirect:";
    }

    @GetMapping("/participacion/sale/esp")
    public String saleParticipacionEsp() {
        c.enviarMensaje(ipfBuilderCartones.saleParticipacionEsp());
        return "redirect:";
    }

    //RESULTADOS

    @GetMapping("/resultados/entra")
    public String resultadosEntra() {
        c.enviarMensaje(ipfBuilderCartones.resultadosEntra());
        return "redirect:";
    }

    @GetMapping("/resultados/entra/delay")
    public String resultadosEntraDelay() {
        c.enviarMensaje(ipfBuilderCartones.resultadosEntraDelay());
        return "redirect:";
    }

    @GetMapping("/resultados/sale")
    public String resultadosSale() {
        c.enviarMensaje(ipfBuilderCartones.resultadosSale());
        return "redirect:";
    }

    @GetMapping("/resultados/cambia")
    public String resultadosCambia() {
        c.enviarMensaje(ipfBuilderCartones.resultadosCambiaAuto());
        return "redirect:";
    }

    @GetMapping("/resultados/sondeo/entra")
    public String resultadosSondeoEntra() {
        c.enviarMensaje(ipfBuilderCartones.resultadosSondeoEntra());
        return "redirect:";
    }

    @GetMapping("/resultados/sondeo/entra/delay")
    public String resultadosSondeoEntraDelay() {
        c.enviarMensaje(ipfBuilderCartones.resultadosSondeoEntraDelay());
        return "redirect:";
    }

    @GetMapping("/resultados/sondeo/sale")
    public String resultadosSondeoSale() {
        c.enviarMensaje(ipfBuilderCartones.resultadosSondeoSale());
        return "redirect:";
    }

    @GetMapping("/resultados/sondeo/cambia")
    public String resultadosSondeoCambia() {
        c.enviarMensaje(ipfBuilderCartones.resultadosSondeoCambiaAuto());
        return "redirect:";
    }


}
