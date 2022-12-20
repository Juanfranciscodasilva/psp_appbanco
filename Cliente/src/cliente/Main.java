package cliente;

import Ventanas.*;
import Clases.*;
import Utils.AccionUtil;
import Utils.FirmaUtil;
import Utils.UsuarioUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;

public class Main {
    
    //Conexion
    public static final String HOST = "localhost";
    public static final int PORT = 54321;
    public static Socket socket = null;
    
    //Cifrador
    public static Cipher cifrador = null;
    
    //Descifrador
    public static Cipher desCypher = null;
    
    //Flujo
    public static ObjectInputStream flujoInput = null;
    public static ObjectOutputStream flujoOutput = null;
    
    //Ventanas
    public static VCargando vCargando;
    public static VIniciarSesion vIniciarSesion;
    public static VRegistrarUsuario vRegistrarUsuario;
    public static VSeleccionAccion vSeleccionAccion;
    
    private static SecretKey clavePublicaServer = null;

    public static void main(String[] args) throws Exception {
        try{
            vCargando = new VCargando();
            vCargando.setVisible(true);
            //Conexion con el servidor
            try {
                socket = new Socket(HOST, PORT);
            } catch (Exception ex) {
                System.out.println("No se ha podido conectar con el servidor\n"+ex.getMessage());
                throw new RuntimeException(ex);
            }

            try{
                flujoInput = new ObjectInputStream(socket.getInputStream());
                flujoOutput = new ObjectOutputStream(socket.getOutputStream());
            }catch(Exception ex){
                System.out.println("Ha ocurrido un error al crear el flujo");
                throw new RuntimeException(ex);
            }

            try{
                //Recibir clave simetrica
                SecretKey key = (SecretKey) flujoInput.readObject();
                System.out.println("le clave es : " + key);
                clavePublicaServer = key;
            }catch(Exception ex){
                System.out.println("Ha ocurrido un error al recibir la clave");
                throw new RuntimeException(ex);
            }

            //Preparar encriptador
            try{
                System.out.println("Configurando Cipher para encriptar");
                cifrador = Cipher.getInstance("DES");
                cifrador.init(Cipher.ENCRYPT_MODE, clavePublicaServer);
            }catch(Exception ex){
                System.out.println("Ha ocurrido un error al preparar el desencriptador");
                throw new RuntimeException(ex);
            }
            
            //Preparar descifrador
            try{
                System.out.println("Configurando Cipher para descrifrar");
                desCypher = Cipher.getInstance("DES");
                desCypher.init(Cipher.DECRYPT_MODE, clavePublicaServer);
            }catch(Exception ex){
                System.out.println("Ha ocurrido un error al preparar el desencriptador");
                throw new RuntimeException(ex);
            }

            abrirLogin();
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }finally{
            if(vCargando != null){
                vCargando.setVisible(false);
                vCargando.dispose();
            }
        }
        
    }
    
    public static void abrirLogin(){
        if(vRegistrarUsuario != null){
            vRegistrarUsuario.setVisible(false);
            vRegistrarUsuario.dispose();
        }
        vIniciarSesion = new VIniciarSesion();
        vIniciarSesion.setVisible(true);
    }
    
    public static void abrirRegistro(){
        if(vIniciarSesion != null){
            vIniciarSesion.setVisible(false);
            vIniciarSesion.dispose();
        }
        vRegistrarUsuario = new VRegistrarUsuario();
        vRegistrarUsuario.setVisible(true);
    }
    
    public static void abrirBanco(){
        if(vIniciarSesion != null){
            vIniciarSesion.setVisible(false);
            vIniciarSesion.dispose();
        }
        vSeleccionAccion = new VSeleccionAccion();
        vSeleccionAccion.setVisible(true);
    }
    
    public static Response solicitarSaldo(String cuenta){
        try{
            Accion accion = AccionUtil.crearAccionSaldo(cuenta);
            byte[] accionCifrada = AccionUtil.cifrarAccion(cifrador, accion);
            flujoOutput.writeObject(accionCifrada);
            return recibirRespuesta();
        }catch(Exception ex){
            System.out.println("Ha habido un error al solicitar el saldo");
            System.out.println(ex.getMessage());
            Response respuesta = new Response();
            respuesta.setCorrecto(false);
            respuesta.setMensajeError("Ha habido un error al solicitar el saldo");
            return respuesta;
        }
    }
    
    public static Response ingresarDinero(String cuenta, int importe){
        try{
            Accion accion = AccionUtil.crearAccionIngresar(cuenta, importe);
            byte[] accionCifrada = AccionUtil.cifrarAccion(cifrador, accion);
            flujoOutput.writeObject(accionCifrada);
            return recibirRespuesta();
        }catch(Exception ex){
            System.out.println("Ha habido un error al ingresar dinero");
            System.out.println(ex.getMessage());
            Response respuesta = new Response();
            respuesta.setCorrecto(false);
            respuesta.setMensajeError("Ha habido un error al ingresar dinero");
            return respuesta;
        }
    }
    
    public static Response retirarDinero(String cuenta, int importe){
        try{
            Accion accion = AccionUtil.crearAccionRetirar(cuenta, importe);
            byte[] accionCifrada = AccionUtil.cifrarAccion(cifrador, accion);
            flujoOutput.writeObject(accionCifrada);
            return recibirRespuesta();
        }catch(Exception ex){
            System.out.println("Ha habido un error al retirar dinero");
            System.out.println(ex.getMessage());
            Response respuesta = new Response();
            respuesta.setCorrecto(false);
            respuesta.setMensajeError("Ha habido un error al retirar dinero");
            return respuesta;
        }
    }
    
    public static Response iniciarSesion(Usuario usu){
        try{
            byte[] usuarioCifrado = UsuarioUtil.cifrarUsuario(cifrador, usu);
            flujoOutput.writeObject(usuarioCifrado);
            return recibirRespuesta();
        }catch(Exception ex){
            System.out.println("Ha habido un error al iniciar sesión");
            System.out.println(ex.getMessage());
            Response respuesta = new Response();
            respuesta.setCorrecto(false);
            respuesta.setMensajeError("Ha habido un error al iniciar sesión");
            return respuesta;
        }
    }
    
    public static Response registrarUsuario(Usuario u){
        vCargando = new VCargando("Registrando...");
        vCargando.setVisible(true);
        try{
            System.out.println(u.getPassword());
            byte[] usuarioCifrado = UsuarioUtil.cifrarUsuario(cifrador, u);
            flujoOutput.writeObject(usuarioCifrado);
            FirmaUtil.FirmarCertificado(flujoInput);
            return recibirRespuesta();
        }catch(Exception ex){
            System.out.println("Ha habido un error al registrar el usuario");
            System.out.println(ex.getMessage());
            Response respuesta = new Response();
            respuesta.setCorrecto(false);
            respuesta.setMensajeError("Ha habido un error al registrar el usuario");
            return respuesta;
        }finally{
            if(vCargando != null){
                vCargando.setVisible(false);
                vCargando.dispose();
            }
        }
    }
    
    public static Response recibirRespuesta(){
        Response r = null;
        try {
            byte[] respuestaRecibida = new byte[]{};
            try {
                respuestaRecibida = (byte[]) flujoInput.readObject();
            } catch (ClassNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
            byte[] respuestaRecibidaDescifrada = desCypher.doFinal(respuestaRecibida);
            ByteArrayInputStream byteInput = new ByteArrayInputStream(respuestaRecibidaDescifrada);
            ObjectInputStream objectInput = new ObjectInputStream(byteInput);
            r = (Response)objectInput.readObject();
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
        return r;
    }
    
    public static void cerrarPrograma(){
        try{
            if(socket != null && !socket.isClosed()){
                socket.close();
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }finally{
            System.exit(0);
        }
    }
    
}
