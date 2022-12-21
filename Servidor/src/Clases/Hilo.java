package Clases;

import Utils.ResponseUtil;
import Utils.UsuarioUtils;

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
            boolean salir = false;
            do{
                System.out.println("Esperando acciones");
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
                }else if(accion.getAccion().equalsIgnoreCase("salir")){
                    salir = true;
                }
                System.out.println("Se ha enviado una respuesta");
                enviarRespuesta(respuesta);
            }while(!salir);
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
        Response respuesta = new Response();
        try{
            CuentaBancaria cta = comprobarCuentaDelUsuario(accion.getCuentaBancaria());
            if(cta == null){
                respuesta.setCorrecto(false);
                respuesta.setMensajeError("La cuenta con número: '"+accion.getCuentaBancaria()+"' no es la correspondiente a su persona, por favor indique su número de cuenta.");
                return respuesta;
            }
            int saldoActual = cta.getSaldo();
            if(saldoActual < accion.getImporte()){
                respuesta.setCorrecto(false);
                respuesta.setMensajeError("Saldo insuficiente.");
                return respuesta;
            }
            CuentaBancaria ctaDestino = this.ListaCuentasBancarias.stream()
                    .filter(c -> c.getNumeroCuenta().equalsIgnoreCase(accion.getCuentaBancaria2()))
                    .findFirst()
                    .orElse(null);

            if(ctaDestino == null){
                respuesta.setCorrecto(false);
                respuesta.setMensajeError("No se ha encontrado un destinatario con número de cuenta: "+accion.getCuentaBancaria2());
                return respuesta;
            }
            cta.setSaldo(saldoActual-accion.getImporte());
            ctaDestino.setSaldo(ctaDestino.getSaldo()+accion.getImporte());
            return respuesta;
        }catch (Exception ex){
            respuesta.setCorrecto(false);
            respuesta.setMensajeError("Ha ocurrido un error al realizar la transferencia");
            return respuesta;
        }
    }

    private Response realizarAccionRetirar(Accion accion){
        Response respuesta = new Response();
        try{
            CuentaBancaria cta = comprobarCuentaDelUsuario(accion.getCuentaBancaria());
            if(cta == null){
                respuesta.setCorrecto(false);
                respuesta.setMensajeError("La cuenta con número: '"+accion.getCuentaBancaria()+"' no es la correspondiente a su persona, por favor indique su número de cuenta.");
                return respuesta;
            }
            int saldoActual = cta.getSaldo();
            if(saldoActual < accion.getImporte()){
                respuesta.setCorrecto(false);
                respuesta.setMensajeError("Saldo insuficiente.");
                return respuesta;
            }
            cta.setSaldo(saldoActual-accion.getImporte());
            return respuesta;
        }catch (Exception ex){
            respuesta.setCorrecto(false);
            respuesta.setMensajeError("Ha ocurrido un error al retirar dinero");
            return respuesta;
        }
    }
    private Response realizarAccionIngresar(Accion accion){
        Response respuesta = new Response();
        try{
            CuentaBancaria cta = comprobarCuentaDelUsuario(accion.getCuentaBancaria());
            if(cta == null){
                respuesta.setCorrecto(false);
                respuesta.setMensajeError("La cuenta con número: '"+accion.getCuentaBancaria()+"' no es la correspondiente a su persona, por favor indique su número de cuenta.");
                return respuesta;
            }
            cta.setSaldo(cta.getSaldo()+accion.getImporte());
            return respuesta;
        }catch (Exception ex){
            respuesta.setCorrecto(false);
            respuesta.setMensajeError("Ha ocurrido un error al ingresar dinero");
            return respuesta;
        }
    }

    private void recibirUsuario() throws Exception{
        boolean registro = false;
        do {
            try {
                Usuario usuario = null;
                byte[] usuarioRecibido = new byte[]{};
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
                        registro = true;
                    }else{
                        respuesta = comprobarInicio(usuario);
                        registro = false;
                    }
                    enviarRespuesta(respuesta);

            }catch(Exception ex){
                System.out.println(ex.getMessage());
                Response respuesta = new Response();
                respuesta.setCorrecto(false);
                respuesta.setMensajeError("Ha ocurrido un error inesperado");
                enviarRespuesta(respuesta);
            }
        }while(registro);
    }

    private Response comprobarInicio(Usuario usua){
        Response respuesta = new Response();
        Usuario encontrado = this.ListaUsuarios.stream()
                .filter(u -> u.getDni().equalsIgnoreCase(usua.getDni()) && u.getPassword().equals(usua.getPassword()))
                .findFirst()
                .orElse(null);
        if(encontrado == null){
            respuesta.setCorrecto(false);
            respuesta.setMensajeError("Las credenciales no corresponden a ningún usuario.");
        }else{
            usuarioSesion = encontrado;
            respuesta.setMensajeCorrecto(UsuarioUtils.nombreCompleto(encontrado));
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
        return respuesta;
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
