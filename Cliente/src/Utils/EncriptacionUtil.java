package Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncriptacionUtil {
    private static final String ALGORITMO = "SHA-256";
    
    public static byte[] getDigest(byte[] mensaje) throws NoSuchAlgorithmException {
        MessageDigest algoritmo = MessageDigest.getInstance(ALGORITMO);
        algoritmo.reset();
        algoritmo.update(mensaje);
        return algoritmo.digest();
    }
    
    public static String encriptarString(String cadena) throws Exception{
        byte[] cadenaBytes = cadena.getBytes();
        byte[] cadenaEncriptada = null;
        try {
            cadenaEncriptada = getDigest(cadenaBytes);
        } catch (Exception ex) {
            throw ex;
        }
        return Base64.getEncoder().encodeToString(cadenaEncriptada);
    }
}
