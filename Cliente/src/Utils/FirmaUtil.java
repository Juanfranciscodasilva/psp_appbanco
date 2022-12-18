package Utils;

import java.io.ObjectInputStream;
import java.security.PublicKey;
import java.security.Signature;

public class FirmaUtil {
    public static void FirmarCertificado(ObjectInputStream flujoInput){
        //Obtener la clave publica del servidor RSA
        PublicKey clavePublica = null;
        try {
            clavePublica = (PublicKey) flujoInput.readObject();
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        //Obtener el mensaje del servidor que va a ser firmado posteriormente por el servidor
        String mensaje = null;
        try {
            mensaje = (String) flujoInput.readObject();
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        //Obtener la firma del servidor del mensaje
        byte[] firmaBytes = null;
        try {
            firmaBytes = (byte[]) flujoInput.readObject();
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        //Verificar la firma del servidor
        Signature firma = null;
        try {
            firma = Signature.getInstance("SHA1WITHRSA");
            firma.initVerify(clavePublica);
            firma.update(mensaje.getBytes());
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        boolean verificacion = false;
        try {
            verificacion = firma.verify(firmaBytes);
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        if(verificacion){
            System.out.println("La firma es correcta");
        } else {
            System.out.println("La firma es incorrecta");
        }
    }
}
