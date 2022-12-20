package Utils;

import Clases.Usuario;

public class UsuarioUtils {
    public static String nombreCompleto(Usuario usu){
        StringBuilder cadena = new StringBuilder();
        if(usu != null){
            if(usu.getDni() != null && !usu.getDni().isBlank()){
                cadena.append(usu.getDni()+", ");
            }
            if(usu.getNombre() != null && !usu.getNombre().isBlank()){
                cadena.append(usu.getNombre()+" ");
            }
            if(usu.getApellidos() != null && !usu.getApellidos().isBlank()){
                cadena.append(usu.getApellidos());
            }
        }
        return cadena.toString();
    }
}
