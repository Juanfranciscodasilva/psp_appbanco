package Clases;

import Utils.ResponseUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.net.Socket;
import java.security.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Hilo extends Thread {
    
    Socket cliente;

    List<Usuario> ListaUsuarios;
    List<CuentaBancaria> ListaCuentasBancarias;

    //Flujo
    public ObjectInputStream flujoInput = null;
    public ObjectOutputStream flujoOutput = null;

    private SecretKey clavePublicaServer = null;
    private SecretKey clavePublicaClient = null;

    //Usuario iniciado sesion
    Usuario usuarioSesion;

    //Descifrador
    public Cipher desCipher = null;
    //Cifrador
    public Cipher cifrador = null;

    //Parametros de creacion
    public final int LONGITUD_CUENTA_BANCARIA = 12;
    
    public Hilo(Socket cliente,List<Usuario> usuarios,List<CuentaBancaria> cuentasBancarias){
        this.cliente = cliente;
        this.ListaUsuarios = usuarios;
        this.ListaCuentasBancarias = cuentasBancarias;
    }
    
    @Override
    public void run(){
        System.out.println("Conexion con el cliente");
        try{
            KeyGenerator keygen = null;
            SecretKey key = null;
            //Generar clave
            try {
                System.out.println("Generando clave");
                keygen = KeyGenerator.getInstance("DES");
                key = keygen.generateKey();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            //Crear cipher para desencriptar
            try {
                System.out.println("Configurando Cipher para desencriptar");
                desCipher = Cipher.getInstance("DES");
                desCipher.init(Cipher.DECRYPT_MODE, key);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            }catch (InvalidKeyException e) {
                e.printStackTrace();
            }

            //Crear cipher para encriptar
            try {
                System.out.println("Configurando Cipher para desencriptar");
                cifrador = Cipher.getInstance("DES");
                cifrador.init(Cipher.ENCRYPT_MODE, key);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            }catch (InvalidKeyException e) {
                e.printStackTrace();
            }

            // Enviar la clave
            flujoOutput = new ObjectOutputStream(cliente.getOutputStream());
            flujoInput = new ObjectInputStream(cliente.getInputStream());
            flujoOutput.writeObject(key);

            recibirUsuario();

            esperarAcciones();

            // cierra los paquetes de datos, el socket y el servidor
            flujoInput.close();
            flujoOutput.close();

            cliente.close();

            System.out.println("Fin de la conexion");
            
        }catch(Exception ex){
            System.out.println("Ha ocurrido un error inesperado.");
            System.out.println(ex.getMessage());
        }
    }

    private void esperarAcciones() throws Exception{
        try{
            byte[] accionRecibida = new byte[]{};
            Accion accion = null;
            do{
                try {
                    accionRecibida = (byte[]) flujoInput.readObject();
                } catch (ClassNotFoundException ex) {
                    System.out.println(ex.getMessage());
                }
                byte[] accionRecibidaDescifrada = desCipher.doFinal(accionRecibida);
                ByteArrayInputStream byteInput = new ByteArrayInputStream(accionRecibidaDescifrada);
                ObjectInputStream objectInput = new ObjectInputStream(byteInput);
                accion = (Accion)objectInput.readObject();
                Response respuesta = new Response();
                if(accion.getAccion().equalsIgnoreCase("saldo")){
                    respuesta = realizarAccionSaldo(accion);
                }else if (accion.getAccion().equalsIgnoreCase("transferencia")) {
                    respuesta = realizarAccionTransferencia(accion);
                }else if (accion.getAccion().equalsIgnoreCase("retirar")) {
                    respuesta = realizarAccionRetirar(accion);
                }else if (accion.getAccion().equalsIgnoreCase("ingresar")) {
                    respuesta = realizarAccionIngresar(accion);
                }
                enviarRespuesta(respuesta);
            }while(accion != null && accion.getAccion().equalsIgnoreCase("salir"));
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            Response respuesta = new Response();
            respuesta.setCorrecto(false);
            respuesta.setMensajeError("Ha ocurrido un error inesperado al realizar una acción de la cuenta");
            enviarRespuesta(respuesta);
        }
    }

    private CuentaBancaria comprobarCuentaDelUsuario(String cuenta) throws Exception{
        try{
            CuentaBancaria cuentaEncontrada = this.ListaCuentasBancarias.stream().filter(c -> c.getNumeroCuenta().equalsIgnoreCase(cuenta))
                    .findFirst().orElse(null);
            if(cuentaEncontrada == null){
                return null;
            }
            if(!cuentaEncontrada.getDniPropietario().equalsIgnoreCase(this.usuarioSesion.getDni())){
                return null;
            }
            return cuentaEncontrada;
        }catch (Exception ex){
            throw ex;
        }
    }

    private Response realizarAccionSaldo(Accion accion){
        Response respuesta = new Response();
        try{
            CuentaBancaria cta = comprobarCuentaDelUsuario(accion.getCuentaBancaria());
            if(cta == null){
                respuesta.setCorrecto(false);
                respuesta.setMensajeError("La cuenta con número: '"+accion.getCuentaBancaria()+"' no es la correspondiente a su persona, por favor indique su número de cuenta.");
                return respuesta;
            }
            respuesta.setMensajeCorrecto(String.valueOf(cta.getSaldo()));
            return respuesta;
        }catch (Exception ex){
            respuesta.setCorrecto(false);
            respuesta.setMensajeError("Ha ocurrido un error al solicitar el saldo");
            return respuesta;
        }
    }

    private Response realizarAccionTransferencia(Accion accion){
        return new Response();
    }

    private Response realizarAccionRetirar(Accion accion){
        return new Response();
    }

    private Response realizarAccionIngresar(Accion accion){
        return new Response();
    }

    private void recibirUsuario() throws Exception{
        try {
            Usuario usuario = null;
            byte[] usuarioRecibido = new byte[]{};
            do {
                try {
                    usuarioRecibido = (byte[]) flujoInput.readObject();
                } catch (ClassNotFoundException ex) {
                    System.out.println(ex.getMessage());
                }
                byte[] usuarioRecibidoDescifrado = desCipher.doFinal(usuarioRecibido);
                ByteArrayInputStream byteInput = new ByteArrayInputStream(usuarioRecibidoDescifrado);
                ObjectInputStream objectInput = new ObjectInputStream(byteInput);
                usuario = (Usuario)objectInput.readObject();
                Response respuesta = new Response();
                if(usuario.isNuevo()){
                    firmarCertificado(flujoOutput);
                    respuesta = crearUsuario(usuario);
                }else{
                    respuesta = comprobarInicio(usuario);
                }
                enviarRespuesta(respuesta);
            }while(usuario != null && usuario.isNuevo());
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            Response respuesta = new Response();
            respuesta.setCorrecto(false);
            respuesta.setMensajeError("Ha ocurrido un error inesperado");
            enviarRespuesta(respuesta);
        }
    }

    private Response comprobarInicio(Usuario usu){
        Response respuesta = new Response();
        Usuario encontrado = this.ListaUsuarios.stream()
                .filter(u -> u.getDni().equalsIgnoreCase(usu.getDni()) && u.getPassword().equals(usu.getPassword()))
                .findFirst()
                .orElse(null);
        if(encontrado == null){
            respuesta.setCorrecto(false);
            respuesta.setMensajeError("Las credenciales no corresponden a ningún usuario.");
        }else{
            usuarioSesion = encontrado;
        }
        return respuesta;
    }

    private Response crearUsuario(Usuario usu) throws Exception{
        Response respuesta = new Response();
        if(!existeUsuario(usu.getDni())){
            usu.setNuevo(false);
            ListaUsuarios.add(usu);
            CuentaBancaria cuenta = new CuentaBancaria();
            String numeroCuenta = generarNumeroCuentaBancaria();
            cuenta.setNumeroCuenta(numeroCuenta);
            cuenta.setSaldo(0);
            cuenta.setDniPropietario(usu.getDni());
            this.ListaCuentasBancarias.add(cuenta);
            respuesta.setMensajeCorrecto(numeroCuenta);
        }else{
            respuesta.setCorrecto(false);
            respuesta.setMensajeError("El usuario con DNI: "+usu.getDni().toUpperCase()+" ya está registrado");
        }
        return new Response();
    }

    private String generarNumeroCuentaBancaria(){
        String cuenta = "";
        do{
            Random random = new Random();
            char[] digits = new char[LONGITUD_CUENTA_BANCARIA];
            digits[0] = (char) (random.nextInt(9) + '1');
            for (int i = 1; i < LONGITUD_CUENTA_BANCARIA; i++) {
                digits[i] = (char) (random.nextInt(10) + '0');
            }
            cuenta = new String(digits);
        }while(existeCuenta(cuenta));
        return cuenta;
    }

    private boolean existeUsuario(String dni){
        return ListaUsuarios.stream().filter(u -> u.getDni().equalsIgnoreCase(dni))
                .findFirst().orElse(null) != null;
    }

    private boolean existeCuenta(String cuenta){
        return ListaCuentasBancarias.stream().filter(c -> c.getNumeroCuenta().equals(cuenta))
                .findFirst().orElse(null) != null;
    }

    private void enviarRespuesta(Response respuesta) throws Exception{
        byte[] respuestaCifrada = ResponseUtil.cifrarRespuesta(cifrador, respuesta);
        flujoOutput.writeObject(respuestaCifrada);
    }

    private void firmarCertificado(ObjectOutputStream output){
        //Creamos una clave publica y privada con RSA
        KeyPairGenerator keysGenerador = null;
        try {
            keysGenerador = KeyPairGenerator.getInstance("RSA");
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        KeyPair parKeys = keysGenerador.generateKeyPair();
        PrivateKey clavePrivada = parKeys.getPrivate();
        PublicKey clavePublica = parKeys.getPublic();

        //Enviamos la clave publica
        try {
            output.writeObject(clavePublica);
            output.flush();
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        //Crear un mensaje y firmarlo
        String mensaje = "este es el certificado de seguridad";
        try {
            output.writeObject(mensaje);
            output.flush();
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        //Firmar el mensaje con la clave privada
        Signature firma = null;
        byte[] firmaBytes = null;
        try {
            firma = Signature.getInstance("SHA1WITHRSA");
            firma.initSign(clavePrivada);
            firma.update(mensaje.getBytes());
            firmaBytes = firma.sign();
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        //Enviar la firma
        try {
            output.writeObject(firmaBytes);
            output.flush();
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
