package com.mggcode.cliente_elecciones.conexion;

import com.mggcode.cliente_elecciones.config.Config;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static com.mggcode.cliente_elecciones.config.Config.config;

public class ConexionIPF {
    private static ConexionIPF conexion;
    private String direccion;
    private Socket servidor;

    private DataInputStream datoEntrada = null;
    private DataOutputStream datoSalida = null;


    public ConexionIPF(String address) {
        iniciarControl(address);
    }

    public String getDireccion() {
        return direccion;
    }

    public static ConexionIPF getConexion(String address) {
        if (conexion == null) {
            conexion = new ConexionIPF(address);
        }
        return conexion;
    }

    public void enviarMensaje(String mensaje) {
        try {
            //No lo reconoce IPF al mandarlo como UTF. Añade caracteres especiales al inicio del String
            datoSalida.writeBytes(mensaje);
            System.out.println("Enviando: " + mensaje);
        } catch (IOException ex) {
            System.err.println("Cliente->ERROR: Al enviar mensaje " + ex.getMessage());
        }
    }

    public void iniciarControl(String address) {
        prepararConexion(address);
        conectar();
    }


    private void prepararConexion(String address) {
        Config.getConfiguracion();
        direccion = address;
    }

    private void conectar() {
        try {
            servidor = new Socket(direccion, Integer.parseInt(config.getProperty("puerto")));
            crearFlujosES();
            System.out.println("Cliente->Conectado al servidor...");
        } catch (IOException ex) {
            System.err.println("Cliente->ERROR: Al conectar al servidor en " + direccion + " > " + ex.getMessage());
            try {
                servidor = new Socket("127.0.0.1", Integer.parseInt(config.getProperty("puerto")));
                crearFlujosES();
                System.out.println("Cliente->Conectado en local");
            } catch (IOException e) {
                System.err.println("Cliente->ERROR: Al conectar al IPF local -> " + ex.getMessage());
            }
            // System.exit(-1);
        }
    }

    private void crearFlujosES() {
        try {
            datoEntrada = new DataInputStream(servidor.getInputStream());
            datoSalida = new DataOutputStream(servidor.getOutputStream());
        } catch (IOException ex) {
            System.err.println("ServidorGC->ERROR: crear flujos de entrada y salida " + ex.getMessage());
        }
    }

    public void desconectar() {
        try {
            servidor.close();
            datoEntrada.close();
            datoSalida.close();
            System.out.println("Cliente->Desconectado");
        } catch (IOException ex) {
            System.err.println("Cliente->ERROR: Al desconectar al servidor " + ex.getMessage());
            System.exit(-1);
        }
    }

    public void prueba() {
        System.out.println("FUNCIONA");
    }
}
