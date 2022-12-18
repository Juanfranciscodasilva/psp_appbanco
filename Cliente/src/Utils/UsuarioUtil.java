package Utils;

import Clases.Usuario;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import javax.crypto.Cipher;

public class UsuarioUtil {
    
    public static byte[] cifrarUsuario(Cipher cifrador, Usuario u) throws Exception{
        try{
            byte[] usuarioCifrado;
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
            ObjectOutputStream objectOutput = new ObjectOutputStream(byteOutput);
            objectOutput.writeObject(u);
            usuarioCifrado = cifrador.doFinal(byteOutput.toByteArray());
            return usuarioCifrado;
        }catch(Exception ex){
            System.out.println("Error al cifrar el usuario");
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}
