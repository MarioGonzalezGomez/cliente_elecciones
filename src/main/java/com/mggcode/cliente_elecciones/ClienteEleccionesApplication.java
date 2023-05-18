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


@SpringBootApplication
public class ClienteEleccionesApplication {

    @Bean
    public RestTemplate getresttemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(ClienteEleccionesApplication.class, args);
    }


    @EventListener({ApplicationReadyEvent.class})
    void applicationReadyEvent() {
        //System.out.println("Abriendo cliente");
        runUI();
        browse("http://localhost:9090");
    }

    public static void runUI() {
        String rutaExe = "C:\\Users\\RTVE\\Desktop\\InterfazApp\\interfaz.exe";
        try {
            // Construir el proceso para ejecutar el archivo .exe
            ProcessBuilder pb = new ProcessBuilder(rutaExe);
            pb.start();
        } catch (IOException e) {
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
