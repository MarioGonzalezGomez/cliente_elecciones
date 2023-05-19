package com.mggcode.cliente_elecciones.controller;

import com.mggcode.cliente_elecciones.DTO.CarmenDTO;
import com.mggcode.cliente_elecciones.conexion.ConexionIPF;
import com.mggcode.cliente_elecciones.conexion.ConexionManager;
import com.mggcode.cliente_elecciones.config.Config;
import com.mggcode.cliente_elecciones.model.CircunscripcionPartido;
import com.mggcode.cliente_elecciones.service.municipales.CircunscripcionPartidoService;
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
@RequestMapping("/municipales")
public class MunicipalesIPF {
    private ConexionManager conexionManager;
    private final IPFFaldonesMessageBuilder ipfBuilder;
    private final IPFCartonesMessageBuilder ipfBuilderCartones;
    private final Config conf;
    private final ConexionIPF c;

    @Autowired
    private CircunscripcionPartidoService cpSer;

    public MunicipalesIPF() {
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

    //INFERIOR
    @GetMapping("/carmen/faldon/entra")
    public String faldonEntraMuni() {
        c.enviarMensaje(ipfBuilder.faldonMuniEntra());
        return "redirect:";
    }

    @GetMapping("/carmen/faldon/sondeo/entra")
    public String faldonEntraMuniSondeo() {
        c.enviarMensaje(ipfBuilder.faldonMuniSondeoEntra());
        return "redirect:";
    }

    @GetMapping("/carmen/faldon/sale")
    public String faldonSaleMuni() {
        c.enviarMensaje(ipfBuilder.faldonMuniSale());
        return "redirect:";
    }

    @GetMapping("/carmen/faldon/sondeo/sale")
    public String faldonSaleMuniSondeo() {
        c.enviarMensaje(ipfBuilder.faldonMuniSondeoSale());
        return "redirect:";
    }

    @GetMapping("/carmen/faldon/actualiza")
    public String faldonMuniActualizo() {
        c.enviarMensaje(ipfBuilder.faldonMuniActualizo());
        return "redirect:";
    }

    @GetMapping("/carmen/faldon/encadena")
    public String faldonMuniEncadena() {
        c.enviarMensaje(ipfBuilder.faldonMuniEncadena());
        return "redirect:";
    }

    @GetMapping("/carmen/faldon/deMuniaAuto")
    public String faldonDeMuniaAuto() {
        c.enviarMensaje(ipfBuilder.deMuniAAuto());
        return "redirect:";
    }

    @GetMapping("/carmen/faldon/deMuniSondeoAAutoSondeo")
    public String deMuniSondeoAAutoSondeo() {
        c.enviarMensaje(ipfBuilder.deMuniSondeoAAutoSondeo());
        return "redirect:";
    }

    @GetMapping("/carmen/faldon/deMuniSondeoAMuni")
    public String deMuniSondeoAMuni() {
        c.enviarMensaje(ipfBuilder.deMuniSondeoAMuni());
        return "redirect:";
    }

    @GetMapping("/carmen/faldon/deMuniSondeoAAuto")
    public String deMuniSondeoAAuto() {
        c.enviarMensaje(ipfBuilder.deMuniSondeoAAuto());
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

    //VOTANTES

    @GetMapping("/carmen/votantes/entra")
    public String faldonVotantesEntra() {
        c.enviarMensaje(ipfBuilder.votantesEntra());
        return "redirect:";
    }

    @GetMapping("/carmen/votantes/historico")
    public String faldonVotantesHistEntra() {
        c.enviarMensaje(ipfBuilder.votantesHistorico());
        return "redirect:";
    }

    @GetMapping("/carmen/votantes/sale")
    public String faldonVotantesSale() {
        c.enviarMensaje(ipfBuilder.votantesSale());
        return "redirect:";
    }

    //ARCOS

    @GetMapping("/arco/load")
    public String loadMapaMayorias() {
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

        CircunscripcionPartido seleccionado = cp.stream()
                .filter(partido -> partido.getKey().getPartido().equals(par))
                .findFirst()
                .orElse(null);
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
        CircunscripcionPartido seleccionado = cp.stream()
                .filter(partido -> partido.getKey().getPartido().equals(par))
                .findFirst()
                .orElse(null);

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
        CircunscripcionPartido seleccionado = cp.stream()
                .filter(partido -> partido.getKey().getPartido().equals(par))
                .findFirst()
                .orElse(null);
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
        CircunscripcionPartido seleccionado = cp.stream()
                .filter(partido -> partido.getKey().getPartido().equals(par))
                .findFirst()
                .orElse(null);

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
        CircunscripcionPartido seleccionado = cp.stream()
                .filter(partido -> partido.getKey().getPartido().equals(par))
                .findFirst()
                .orElse(null);

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
        CircunscripcionPartido seleccionado = cp.stream()
                .filter(partido -> partido.getKey().getPartido().equals(par))
                .findFirst()
                .orElse(null);

        System.out.println(ipfBuilderCartones.partidoEntraDer(cp, seleccionado, 3));
        return "redirect:";

        //c.enviarMensaje(ipfBuilder.lateralEntra());
    }

    @GetMapping("/arco/{circunscripcion}/{partido}/{tipoArco}/borrar")
    public String borrarPartido(@PathVariable("circunscripcion") String cir, @PathVariable("partido") String par) {
        CarmenDTO carmenDTO = CarmenDtoReader.getInstance().readCarmenDto(cir);
        List<CircunscripcionPartido> cp = carmenDTO.getCpDTO()
                .stream().map(
                        c -> CircunscripcionPartido.mapFromCpDTO(carmenDTO.getCircunscripcion(), c))
                .filter(x -> x.getEscanos_hasta() > 0.0)
                .toList();

        CircunscripcionPartido seleccionado = cp.stream()
                .filter(partido -> partido.getKey().getPartido().equals(par))
                .findFirst()
                .orElse(null);

        String resultado1 = ipfBuilderCartones.borrarPartido(cp, seleccionado, 1);
        //System.out.println(resultado1);
        c.enviarMensaje(resultado1);
        return "redirect:";
    }

    @GetMapping("/arco/reset")
    public String resetArco() {
        ipfBuilderCartones.reset();
        System.out.println("Reset completado");
        return "redirect:";

        //c.enviarMensaje(ipfBuilderCartones.reset());
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
    public String arcoSondeoEntraDelay() {
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
        c.enviarMensaje(ipfBuilderCartones.participacionCambiaMuni());
        return "redirect:";
    }

    @GetMapping("/participacion/entra/esp")
    public String entraParticipacionEsp() {
        c.enviarMensaje(ipfBuilderCartones.participacionEspMuni());
        return "redirect:";
    }

    @GetMapping("/participacion/sale/esp")
    public String saleParticipacionEsp() {
        c.enviarMensaje(ipfBuilderCartones.saleParticipacionEsp());
        return "redirect:";
    }


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
        c.enviarMensaje(ipfBuilderCartones.resultadosCambiaMuni());
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
        c.enviarMensaje(ipfBuilderCartones.resultadosSondeoCambiaMuni());
        return "redirect:";
    }

}
