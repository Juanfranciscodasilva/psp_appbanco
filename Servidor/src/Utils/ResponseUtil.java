package Utils;

import Clases.Response;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

public class ResponseUtil {

    public static byte[] cifrarRespuesta(Cipher cifrador, Response r) throws Exception{
        try{
            byte[] respuestaCifrada;
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
            ObjectOutputStream objectOutput = new ObjectOutputStream(byteOutput);
            objectOutput.writeObject(r);
            respuestaCifrada = cifrador.doFinal(byteOutput.toByteArray());
            return respuestaCifrada;
        }catch(Exception ex){
            System.out.println("Error al cifrar la respuesta");
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}
