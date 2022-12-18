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
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Hilo extends Thread {
    
    Socket cliente;

    List<Usuario> ListaUsuarios;

    //Flujo
    public static ObjectInputStream flujoInput = null;
    public static ObjectOutputStream flujoOutput = null;

    private static SecretKey clavePublicaServer = null;
    private static SecretKey clavePublicaClient = null;

    //Descifrador
    public static Cipher desCipher = null;
//Cifrador
    public static Cipher cifrador = null;
    
    public Hilo(Socket cliente,List<Usuario> usuarios){
        this.cliente = cliente;
        this.ListaUsuarios = usuarios;
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
                    //TODO registrar el usuario en la lista
                }else{
                    //TODO buscar el usuario y ver si hace login
                }
                enviarRespuesta(respuesta);
            }while(usuario != null && usuario.isNuevo());
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            throw ex;
        }
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
