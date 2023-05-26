package com.mggcode.cliente_elecciones.controller.municipales;


import com.mggcode.cliente_elecciones.ClienteEleccionesApplication;
import com.mggcode.cliente_elecciones.config.Config;
import com.mggcode.cliente_elecciones.data.Data;
import com.mggcode.cliente_elecciones.exception.ConnectionException;
import com.mggcode.cliente_elecciones.model.Circunscripcion;
import com.mggcode.cliente_elecciones.model.Dummy;
import com.mggcode.cliente_elecciones.service.municipales.CarmenDTOService;
import com.mggcode.cliente_elecciones.service.municipales.CircunscripcionService;
import com.mggcode.cliente_elecciones.service.municipales.SedesDTOService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;


@RestController
@RequestMapping("/municipales/circunscripciones")
public class CircunscripcionController {

    @Autowired
    private CircunscripcionService circunscripcionService;

    @Autowired
    private CarmenDTOService carmenDTOService;

    @Autowired
    private SedesDTOService sedesDTOService;

    List<Circunscripcion> circunscripciones = new ArrayList<>();
    AtomicBoolean isSuscribed = new AtomicBoolean(false);
    List<Circunscripcion> changes;
    ReentrantLock lock = new ReentrantLock();

    Data data = Data.getInstance();
    private boolean oficiales = true;


    @GetMapping
    public ResponseEntity<Dummy> verCircunscripciones(Model model) throws ConnectionException {
        List<Circunscripcion> circunscripciones = circunscripcionService.findAll();
        model.addAttribute("circunscripciones", circunscripciones);
        model.addAttribute("tipo", "municipales");
        model.addAttribute("ruta", "/municipales/circunscripciones");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/selected/oficial/f_autonomicas/{codigo}")
    public ResponseEntity<Dummy> selectCircunscripcionAutonomiaOficial(@PathVariable("codigo") String codigo, Model model) {
        System.out.println("---" + codigo);
        Data data = Data.getInstance();
        data.setCircunscripcionSeleccionada(codigo);
        oficiales = true;
        try {
            lock.lock();
            carmenDTOService.writeCricunscripcionSeleccionadaOficial(data.getCircunscripcionSeleccionada());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/selected/oficial/mapa_mayorias/{codigo}")
    public ResponseEntity<Dummy> selectCircunscripcionMapaOficial(@PathVariable("codigo") String codigo, Model model) {
        System.out.println("---" + codigo);
        Data data = Data.getInstance();
        data.setCircunscripcionSeleccionada(codigo);
        oficiales = true;
        try {
            lock.lock();

            carmenDTOService.writeAutonomiaSeleccionadaArcoMayoriasOficial(data.getCircunscripcionSeleccionada());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/selected/sondeo/f_autonomicas/{codigo}")
    public ResponseEntity<Dummy> selectCircunscripcionAutnomiaSondeo(@PathVariable("codigo") String codigo, Model model) {
        System.out.println("---" + codigo);
        Data data = Data.getInstance();
        data.setCircunscripcionSeleccionada(codigo);
        oficiales = false;
        try {
            lock.lock();

            carmenDTOService.writeCricunscripcionSeleccionadaSondeo(data.getCircunscripcionSeleccionada());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/selected/sondeo/mapa_mayorias/{codigo}")
    public ResponseEntity<Dummy> selectCircunscripcionMapaSondeo(@PathVariable("codigo") String codigo, Model model) {
        System.out.println("---" + codigo);
        Data data = Data.getInstance();
        data.setCircunscripcionSeleccionada(codigo);
        oficiales = false;
        try {
            lock.lock();

            carmenDTOService.writeAutonomiaSeleccionadaArcoMayoriasSondeo(data.getCircunscripcionSeleccionada());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @RequestMapping(path = "/csv")
    public ResponseEntity<Dummy> findAllInCsv(RedirectAttributes redirectAttributes) throws IOException {
        circunscripcionService.findAllInCsv();
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @RequestMapping(path = "/excel")
    public ResponseEntity<Dummy> findAllInExcel(RedirectAttributes redirectAttributes) throws IOException {
        circunscripcionService.findAllInExcel();
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Dummy> verCircunscripcionDetalle(@PathVariable("codigo") String cod, Model model, @RequestHeader("Referer") String referer) {
        Circunscripcion circunscripcion = circunscripcionService.findById(cod);
        model.addAttribute("circunscripcion", circunscripcion);
        model.addAttribute("referer", referer);
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/{codigo}/data")
    public ResponseEntity<Circunscripcion> getCircunscripcionPorId(@PathVariable("codigo") String cod) {
        return new ResponseEntity<>(circunscripcionService.findById(cod), HttpStatus.OK);
    }

    @RequestMapping(path = "/{codigo}/csv")
    public ResponseEntity<Dummy> findByIdInCsv(@PathVariable("codigo") String codigo) throws IOException {
        circunscripcionService.findByIdInCsv(codigo);
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @RequestMapping(path = "/{codigo}/excel")
    public ResponseEntity<Dummy> findByIdInExcel(@PathVariable("codigo") String codigo) throws IOException {
        circunscripcionService.findByIdInExcel(codigo);
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    @GetMapping("/municipios/{codigo}")
    public ResponseEntity<List<Circunscripcion>> findByAutonomia(@PathVariable("codigo") String codigo) {
        return new ResponseEntity<>(circunscripcionService.findByAutonomia(codigo), HttpStatus.OK);
    }

    @GetMapping("/filtrada/{codigo}")
    public ResponseEntity<List<Circunscripcion>> filtradasPorMostrar(@PathVariable("codigo") String codigo) throws ConnectionException {
        List<Circunscripcion> circunscripcions = circunscripcionService.filtradasPorMostrar();
        List<Circunscripcion> resultado = circunscripcions.stream().filter(c -> c.getCodigoComunidad().equals(codigo)).toList();
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    @GetMapping("/autonomias")
    public ResponseEntity<List<Circunscripcion>> findAutonomiasMuni() {
        List<Circunscripcion> res = null;
        try {
            res = circunscripcionService.findAll().stream()
                    .filter(c -> c.getCodigoProvincia().equals("00") && c.getCodigoMunicipio().equals("000"))
                    .toList();
            return new ResponseEntity<>(res, HttpStatus.OK);

        } catch (ConnectionException e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }
    }

    public void suscribeCircunscripciones() {
        if (!isSuscribed.get()) {
            System.out.println("Suscribiendo municipales...");
            isSuscribed.set(true);
            ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
            exec.scheduleAtFixedRate(() -> {
                if (!ClienteEleccionesApplication.closeCheck) {
                    System.out.println("Cerrando...");
                    exec.shutdown();
                }
                if (circunscripciones.isEmpty()) {
                    System.out.println("Cargando partidos");
                    try {
                        circunscripciones = circunscripcionService.findAll();
                    } catch (ConnectionException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    List<Circunscripcion> circunscripcionesNew;
                    try {
                        circunscripcionesNew = circunscripcionService.findAll();
                    } catch (ConnectionException e) {
                        throw new RuntimeException(e);
                    }
                    if (!circunscripcionesNew.equals(circunscripciones)) {
                        System.out.println("Cambios detectados");
                        getChanges(circunscripciones, circunscripcionesNew);
                        if (containsSelected(data.circunscripcionSeleccionada)) {
                            System.out.println("Seleccionada ha cambiado");
                            try {
                                lock.lock();
                                if (oficiales) {
                                    updateSelectedOficial();
                                } else {
                                    updateSelectedSondeo();
                                }
                            } catch (IOException e) {
                                System.err.println(e.getMessage());
                                throw new RuntimeException(e);
                            } finally {
                                lock.unlock();
                            }
                        }
                        circunscripciones = circunscripcionesNew;
                    }
                }
            }, 0, 30, TimeUnit.SECONDS);
        }
    }

    private void updateSelectedOficial() throws IOException {
        carmenDTOService.writeCricunscripcionSeleccionadaOficial(data.getCircunscripcionSeleccionada());
        carmenDTOService.writeAutonomiaSeleccionadaArcoMayoriasOficial(data.getCircunscripcionSeleccionada());
    }

    private void updateSelectedSondeo() throws IOException {
        carmenDTOService.writeCricunscripcionSeleccionadaSondeo(data.getCircunscripcionSeleccionada());
        carmenDTOService.writeAutonomiaSeleccionadaArcoMayoriasSondeo(data.getCircunscripcionSeleccionada());
    }

    private boolean containsSelected(String codigo) {
        if (codigo.isBlank())
            return false;
        var found = circunscripcionService.findById(codigo);
        return changes.contains(circunscripcionService.findById(codigo));
    }

    private void getChanges(List<Circunscripcion> oldList, List<Circunscripcion> newList) {
        List<Circunscripcion> differences = newList.stream()
                .filter(element -> !oldList.contains(element))
                .toList();
        changes = differences;
    }

    private final String ruta = Config.config.getProperty("rutaFicheros");

    @GetMapping("/update/espania")
    public ResponseEntity<Dummy> updateEspania() {
        File carpetaBase = comprobarCarpetas();
        URL url = null;
        try {
            url = new URL("http://" + Config.connectedServer + ":8080/municipales/carmen/oficial/9900000/csv");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        File csv = new File(carpetaBase.getPath() + File.separator + "F_9900000.csv");
        try {
            FileUtils.copyURLToFile(url, csv);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Dummy dummy = new Dummy("202 OK");
        return new ResponseEntity<>(dummy, HttpStatus.OK);
    }

    private File comprobarCarpetas() {
        File datos = new File(ruta);
        if (!datos.exists()) {
            datos.mkdir();
        }
        return datos;
    }
}