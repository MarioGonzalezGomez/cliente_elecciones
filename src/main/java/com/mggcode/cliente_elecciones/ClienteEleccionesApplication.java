package com.mggcode.cliente_elecciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;


@SpringBootApplication
public class ClienteEleccionesApplication {

    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    @Bean
    public RestTemplate getresttemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(ClienteEleccionesApplication.class, args);
    }


    @EventListener({ApplicationReadyEvent.class})
    void applicationReadyEvent() throws InterruptedException {
        //System.out.println("Abriendo cliente");
        browse("http://localhost:9090");
        Thread.sleep(1000);
        System.out.println(ANSI_GREEN + "INICIANDO INTERFAZ" + ANSI_RESET);
        runClient();
    }

    public static void runClient() {
        String ruta = Paths.get("").toAbsolutePath().toString() + "\\script.bat";
        try {
            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", ruta);
            pb.inheritIO();
            Process proceso = pb.start();

            int resultado = proceso.waitFor();

            if (resultado == 0) {
                System.out.println("El archivo .bat se ejecutó correctamente.");
            } else {
                System.out.println("Se produjo un error al ejecutar el archivo .bat.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void browse(String url) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
