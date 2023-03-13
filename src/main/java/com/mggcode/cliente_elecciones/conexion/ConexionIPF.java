package com.mggcode.cliente_elecciones.conexion;

import com.mggcode.cliente_elecciones.config.ConfigIPF;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static com.mggcode.cliente_elecciones.config.ConfigIPF.config;

public class ConexionIPF {
    private static ConexionIPF conexion;

    private String direccion;
    private Socket servidor;

    private DataInputStream datoEntrada = null;
    private DataOutputStream datoSalida = null;


    private ConexionIPF() {
        iniciarControl();
    }

    public static ConexionIPF getConexion() {
        if (conexion == null) {
            conexion = new ConexionIPF();
        }
        return conexion;
    }

    public void enviarMensaje(String mensaje) {
        try {
            //No lo reconoce IPF al mandarlo como UTF. Añade caracteres especiales al inicio del String
            datoSalida.writeBytes(mensaje);
        } catch (IOException ex) {
            System.err.println("Cliente->ERROR: Al enviar mensaje " + ex.getMessage());
        }
    }

    public void iniciarControl() {
        prepararConexion();
        conectar();
    }

    private void prepararConexion() {
        ConfigIPF.getConfiguracion();
        if (config.getProperty("direccion").equals("0")) {
            direccion = "localhost";
        } else {
            direccion = config.getProperty("direccion");
        }
    }

    private void conectar() {
        try {
            servidor = new Socket(direccion, Integer.parseInt(config.getProperty("puerto")));
            crearFlujosES();
            System.out.println("Cliente->Conectado al servidor...");
        } catch (IOException ex) {
            System.err.println("Cliente->ERROR: Al conectar al servidor " + ex.getMessage());
            System.exit(-1);
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

}
