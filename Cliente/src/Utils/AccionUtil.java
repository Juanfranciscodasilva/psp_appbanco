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
    
    public static Accion crearAccionIngresar(String cuenta,int importe){
        Accion accion = new Accion();
        accion.setAccion("ingresar");
        accion.setCuentaBancaria(cuenta);
        accion.setImporte(importe);
        return accion;
    }
    
    public static Accion crearAccionRetirar(String cuenta,int importe){
        Accion accion = new Accion();
        accion.setAccion("retirar");
        accion.setImporte(importe);
        accion.setCuentaBancaria(cuenta);
        return accion;
    }
    
    public static Accion crearAccionTransferencia(String cuentaEmisor, int importe, String cuentaDestino){
        Accion accion = new Accion();
        accion.setAccion("transferencia");
        accion.setCuentaBancaria(cuentaEmisor);
        accion.setImporte(importe);
        accion.setCuentaBancaria2(cuentaDestino);
        return accion;
    }
}
