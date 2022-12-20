package Utils;

import Clases.Accion;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import javax.crypto.Cipher;

public class AccionUtil {
    
    public static byte[] cifrarAccion(Cipher cifrador, Accion a) throws Exception{
        try{
            byte[] accionCifrada;
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
            ObjectOutputStream objectOutput = new ObjectOutputStream(byteOutput);
            objectOutput.writeObject(a);
            accionCifrada = cifrador.doFinal(byteOutput.toByteArray());
            return accionCifrada;
        }catch(Exception ex){
            System.out.println("Error al cifrar la acción");
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    
    public static Accion crearAccionSaldo(String cuenta){
        Accion accion = new Accion();
        accion.setAccion("saldo");
        accion.setCuentaBancaria(cuenta);
        return accion;
    }
}
