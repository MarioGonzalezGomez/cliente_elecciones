package com.mggcode.cliente_elecciones.controller;

import com.mggcode.cliente_elecciones.DTO.CarmenDTO;
import com.mggcode.cliente_elecciones.conexion.ConexionIPF;
import com.mggcode.cliente_elecciones.conexion.ConexionManager;
import com.mggcode.cliente_elecciones.config.Config;
import com.mggcode.cliente_elecciones.model.CircunscripcionPartido;
import com.mggcode.cliente_elecciones.model.Dummy;
import com.mggcode.cliente_elecciones.service.autonomicas.ACircunscripcionPartidoService;
import com.mggcode.cliente_elecciones.utils.CarmenDtoReader;
import com.mggcode.cliente_elecciones.utils.IPFCartonesMessageBuilder;
import com.mggcode.cliente_elecciones.utils.IPFFaldonesMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
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
    public ResponseEntity<Dummy> resetIPF() {
        c.enviarMensaje(ipfBuilder.resetIPF());
        c.enviarMensaje(ipfBuilderCartones.resetIPF());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);

    }

    //PACTOS
    @GetMapping("/carmen/pactos/entra")
    public ResponseEntity<Dummy> entraPactos() {
        c.enviarMensaje(ipfBuilder.pactosEntra());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/carmen/pactos/sale")
    public ResponseEntity<Dummy> salePactos() {
        c.enviarMensaje(ipfBuilder.pactosSale());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/carmen/pactos/reinicio")
    public ResponseEntity<Dummy> reinicioPactos() {
        c.enviarMensaje(ipfBuilder.pactosReinicio());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/carmen/pactos/{posicion}/entraIzq")
    public ResponseEntity<Dummy> entraIzqPactos(@PathVariable("posicion") int posicion) {
        c.enviarMensaje(ipfBuilder.pactosEntraIzquierda(posicion));
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/carmen/pactos/{posicion}/entraDer")
    public ResponseEntity<Dummy> entraDerPactos(@PathVariable("posicion") int posicion) {
        c.enviarMensaje(ipfBuilder.pactosEntraDerecha(posicion));
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }


    //LATERAL

    @GetMapping("/carmen/lateral/entra")
    public ResponseEntity<Dummy> entraFaldonLateral() {
        c.enviarMensaje(ipfBuilder.lateralEntra());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);

    }

    @GetMapping("/carmen/lateral/{codigo}/despliega")
    public ResponseEntity<Dummy> despliegaFaldonLateral(@PathVariable("codigo") String codCircunscripcion) {
        c.enviarMensaje(ipfBuilder.lateralDespliega(codCircunscripcion));
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);

    }

    @GetMapping("/carmen/lateral/{codigo}/repliega")
    public ResponseEntity<Dummy> repliegaFaldonLateral(@PathVariable("codigo") String codCircunscripcion) {
        c.enviarMensaje(ipfBuilder.lateralRepliega(codCircunscripcion));
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);

    }

    @GetMapping("/carmen/lateral/{codigo}/actualiza")
    public ResponseEntity<Dummy> actualizaFaldonLateral(@PathVariable("codigo") String codCircunscripcion) {
        c.enviarMensaje(ipfBuilder.lateralActualiza(codCircunscripcion));
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/carmen/lateral/actualiza")
    public ResponseEntity<Dummy> actualizaFaldonLateral() {
        c.enviarMensaje(ipfBuilder.lateralActualiza());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/carmen/lateral/sale")
    public ResponseEntity<Dummy> saleFaldonLateral() {
        c.enviarMensaje(ipfBuilder.lateralSale());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    //INFERIOR

    @GetMapping("/carmen/faldon/entra")
    public ResponseEntity<Dummy> faldonEntraAuto() {
        c.enviarMensaje(ipfBuilder.faldonAutoEntra());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/carmen/faldon/sondeo/entra")
    public ResponseEntity<Dummy> faldonEntraAutoSondeo() {
        c.enviarMensaje(ipfBuilder.faldonAutoSondeoEntra());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/carmen/faldon/sale")
    public ResponseEntity<Dummy> faldonSaleAuto() {
        c.enviarMensaje(ipfBuilder.faldonAutoSale());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/carmen/faldon/sondeo/sale")
    public ResponseEntity<Dummy> faldonSaleAutoSondeo() {
        c.enviarMensaje(ipfBuilder.faldonAutoSondeoSale());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/carmen/faldon/actualiza")
    public ResponseEntity<Dummy> faldonAutoActualizo() {
        c.enviarMensaje(ipfBuilder.faldonAutoActualizo());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/carmen/faldon/encadena")
    public ResponseEntity<Dummy> faldonAutoEncadena() {
        c.enviarMensaje(ipfBuilder.faldonAutoEncadena());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/carmen/faldon/sondeo/encadena")
    public ResponseEntity<Dummy> faldonAutoSondeoEncadena() {
        c.enviarMensaje(ipfBuilder.faldonAutoSondeoEncadena());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/carmen/faldon/deAutoaMuni")
    public ResponseEntity<Dummy> faldonDeMuniaAuto() {
        c.enviarMensaje(ipfBuilder.deAutoAMuni());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/carmen/faldon/deMuniASondeoAuto")
    public ResponseEntity<Dummy> deMuniASondeoAuto() {
        c.enviarMensaje(ipfBuilder.deMuniASondeoAuto());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/carmen/faldon/deAutoSondeoAMuniSondeo")
    public ResponseEntity<Dummy> deAutoSondeoAMuniSondeo() {
        c.enviarMensaje(ipfBuilder.deAutoSondeoAMuniSondeo());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/carmen/faldon/deAutoSondeoAMuni")
    public ResponseEntity<Dummy> deAutoSondeoAMuni() {
        c.enviarMensaje(ipfBuilder.deAutoSondeoAMuni());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/carmen/faldon/deAutoSondeoAAuto")
    public ResponseEntity<Dummy> deAutoSondeoAAuto() {
        c.enviarMensaje(ipfBuilder.deAutoSondeoAAuto());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    //SEDES

    @GetMapping("/carmen/sedes/entra")
    public ResponseEntity<Dummy> faldonSedesEntra() {
        c.enviarMensaje(ipfBuilder.sedesEntra());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/carmen/sedes/sale")
    public ResponseEntity<Dummy> faldonSedesSale() {
        c.enviarMensaje(ipfBuilder.sedesSale());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    //ARCOS
    @GetMapping("/arco/load")
    public ResponseEntity<Dummy> load() {
        c.enviarMensaje(ipfBuilderCartones.load());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);

    }

    @GetMapping("/arco/oficial/{circunscripcion}/{partido}/entraIzq")
    public ResponseEntity<Dummy> entraPartidoIzq1(@PathVariable("circunscripcion") String cir, @PathVariable("partido") String par) {
        CarmenDTO carmenDTO = CarmenDtoReader.getInstance().readCarmenDto(2);
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
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);

    }

    @GetMapping("/arco/principales/{circunscripcion}/{partido}/entraIzq")
    public ResponseEntity<Dummy> entraPartidoIzq2(@PathVariable("circunscripcion") String cir, @PathVariable("partido") String par) {
        CarmenDTO carmenDTO = CarmenDtoReader.getInstance().readCarmenDto(4);
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
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);


    }

    @GetMapping("/arco/sondeo/{circunscripcion}/{partido}/entraIzq")
    public ResponseEntity<Dummy> entraPartidoIzq3(@PathVariable("circunscripcion") String cir, @PathVariable("partido") String par) {
        CarmenDTO carmenDTO = CarmenDtoReader.getInstance().readCarmenDto(4);
        List<CircunscripcionPartido> cp = carmenDTO.getCpDTO()
                .stream().map(
                        c -> CircunscripcionPartido.mapFromCpDTO(carmenDTO.getCircunscripcion(), c))
                .filter(x -> x.getEscanos_hasta_sondeo() > 0.0)
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
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);


    }


    @GetMapping("/arco/oficial/{circunscripcion}/{partido}/entraDer")
    public ResponseEntity<Dummy> entraPartidoDer1(@PathVariable("circunscripcion") String cir, @PathVariable("partido") String par) {
        CarmenDTO carmenDTO = CarmenDtoReader.getInstance().readCarmenDto(2);
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

        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);

    }

    @GetMapping("/arco/principales/{circunscripcion}/{partido}/entraDer")
    public ResponseEntity<Dummy> entraPartidoDer2(@PathVariable("circunscripcion") String cir, @PathVariable("partido") String par) {
        CarmenDTO carmenDTO = CarmenDtoReader.getInstance().readCarmenDto(4);
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
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/arco/sondeo/{circunscripcion}/{partido}/entraDer")
    public ResponseEntity<Dummy> entraPartidoDer3(@PathVariable("circunscripcion") String cir, @PathVariable("partido") String par) {
        CarmenDTO carmenDTO = CarmenDtoReader.getInstance().readCarmenDto(4);
        List<CircunscripcionPartido> cp = carmenDTO.getCpDTO()
                .stream().map(
                        c -> CircunscripcionPartido.mapFromCpDTO(carmenDTO.getCircunscripcion(), c))
                .filter(x -> x.getEscanos_hasta_sondeo() > 0.0)
                .toList();
        CircunscripcionPartido seleccionado = cp.stream()
                .filter(partido -> partido.getKey().getPartido().equals(par))
                .findFirst()
                .orElse(null);
        String resultado1 = ipfBuilderCartones.partidoEntraDer(cp, seleccionado, 3);
        //  System.out.println(resultado1);
        c.enviarMensaje(resultado1);
        String resultado2 = ipfBuilderCartones.partidoEntraDer(cp, seleccionado, 4);
        // System.out.println(resultado2);
        c.enviarMensaje(resultado2);
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/arco/{circunscripcion}/{partido}/{tipoElecciones}/{izquierda}/borrar")
    public ResponseEntity<Dummy> borrarPartido(@PathVariable("circunscripcion") String cir, @PathVariable("partido") String par, @PathVariable("tipoElecciones") int tipoElecciones, @PathVariable("izquierda") int izquierda) {
        CarmenDTO carmenDTO = CarmenDtoReader.getInstance().readCarmenDto(tipoElecciones);
        List<CircunscripcionPartido> cp = carmenDTO.getCpDTO()
                .stream().map(
                        c -> CircunscripcionPartido.mapFromCpDTO(carmenDTO.getCircunscripcion(), c))
                .filter(x -> x.getEscanos_hasta() > 0.0)
                .toList();

        CircunscripcionPartido seleccionado = cp.stream()
                .filter(partido -> partido.getKey().getPartido().equals(par))
                .findFirst()
                .orElse(null);

        int tipoArco = switch (tipoElecciones) {
            case 1, 2 -> 1;
            case 3, 4 -> 3;
            default -> throw new IllegalStateException("Unexpected value: " + tipoElecciones);
        };
        boolean izq = izquierda == 1;
        String resultado;
        if (tipoArco == 3) {
            resultado = ipfBuilderCartones.borrarPartido(cp, seleccionado, tipoArco + 1, izq);
            resultado += ipfBuilderCartones.borrarPartido(cp, seleccionado, tipoArco, izq);

        } else {
            resultado = ipfBuilderCartones.borrarPartido(cp, seleccionado, tipoArco, izq);
        }
        //System.out.println(resultado);
        c.enviarMensaje(resultado);
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/arco/reset")
    public ResponseEntity<Dummy> resetArco() {
        ipfBuilderCartones.reset();
        System.out.println("Reset completado");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);

    }

    @GetMapping("/arco/entra")
    public ResponseEntity<Dummy> arcoEntra() {
        c.enviarMensaje(ipfBuilderCartones.arcoEntra());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/arco/entra/delay")
    public ResponseEntity<Dummy> arcoEntraDelay() {
        c.enviarMensaje(ipfBuilderCartones.arcoEntraDelay());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/arco/sale")
    public ResponseEntity<Dummy> arcoSale() {
        c.enviarMensaje(ipfBuilderCartones.arcoSale());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/arco/pactos")
    public ResponseEntity<Dummy> arcoPactos() {
        c.enviarMensaje(ipfBuilderCartones.arcoPactos());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/arco/sondeo/entra")
    public ResponseEntity<Dummy> arcoSondeoEntra() {
        c.enviarMensaje(ipfBuilderCartones.arcoSondeoEntra());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/arco/sondeo/entra/delay")
    public ResponseEntity<Dummy> arcoSondeoEntraDelayed() {
        c.enviarMensaje(ipfBuilderCartones.arcoSondeoEntraDelay());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }


    @GetMapping("/arco/sondeo/sale")
    public ResponseEntity<Dummy> arcoSondeoSale() {
        c.enviarMensaje(ipfBuilderCartones.arcoSondeoSale());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/arco/sondeo/pactos")
    public ResponseEntity<Dummy> arcoSondeoPactos() {
        c.enviarMensaje(ipfBuilderCartones.arcoSondeoPactos());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    //PARTICIPACION
    @GetMapping("/participacion/entra")
    public ResponseEntity<Dummy> participacionEntra() {
        c.enviarMensaje(ipfBuilderCartones.participacionEntra());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }


    @GetMapping("/participacion/entra/delay")
    public ResponseEntity<Dummy> participacionEntraDelay() {
        c.enviarMensaje(ipfBuilderCartones.participacionEntraDelay());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }


    @GetMapping("/participacion/sale")
    public ResponseEntity<Dummy> participacionSale() {
        c.enviarMensaje(ipfBuilderCartones.participacionSale());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/participacion/cambia")
    public ResponseEntity<Dummy> participacionCambia() {
        c.enviarMensaje(ipfBuilderCartones.participacionCambiaAuto());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }


    @GetMapping("/participacion/entra/esp")
    public ResponseEntity<Dummy> entraParticipacionEsp() {
        c.enviarMensaje(ipfBuilderCartones.participacionEspAuto());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/participacion/entra/esp/delay")
    public ResponseEntity<Dummy> entraParticipacionEspDelay() {
        c.enviarMensaje(ipfBuilderCartones.participacionEspAutoDelay());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/participacion/sale/esp")
    public ResponseEntity<Dummy> saleParticipacionEsp() {
        c.enviarMensaje(ipfBuilderCartones.saleParticipacionEsp());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    //RESULTADOS

    @GetMapping("/resultados/entra")
    public ResponseEntity<Dummy> resultadosEntra() {
        c.enviarMensaje(ipfBuilderCartones.resultadosEntra());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/resultados/entra/delay")
    public ResponseEntity<Dummy> resultadosEntraDelay() {
        c.enviarMensaje(ipfBuilderCartones.resultadosEntraDelay());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/resultados/sale")
    public ResponseEntity<Dummy> resultadosSale() {
        c.enviarMensaje(ipfBuilderCartones.resultadosSale());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/resultados/cambia")
    public ResponseEntity<Dummy> resultadosCambia() {
        c.enviarMensaje(ipfBuilderCartones.resultadosCambiaComunidad());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/resultados/sondeo/entra")
    public ResponseEntity<Dummy> resultadosSondeoEntra() {
        c.enviarMensaje(ipfBuilderCartones.resultadosSondeoEntra());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/resultados/sondeo/entra/delay")
    public ResponseEntity<Dummy> resultadosSondeoEntraDelay() {
        c.enviarMensaje(ipfBuilderCartones.resultadosSondeoEntraDelay());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/resultados/sondeo/sale")
    public ResponseEntity<Dummy> resultadosSondeoSale() {
        c.enviarMensaje(ipfBuilderCartones.resultadosSondeoSale());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/resultados/sondeo/cambia")
    public ResponseEntity<Dummy> resultadosSondeoCambia() {
        c.enviarMensaje(ipfBuilderCartones.resultadosSondeoCambiaComunidad());
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }


}
