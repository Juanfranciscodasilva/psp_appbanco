package Clases;

import java.io.Serializable;

public class Response implements Serializable{

    public boolean correcto;

    public String mensajeError;
    
    public String mensajeCorrecto;

    
    public Response(){
        this.correcto = true;
        this.mensajeError = "";
    }

    public boolean isCorrecto() {
        return correcto;
    }

    public void setCorrecto(boolean correcto) {
        this.correcto = correcto;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public String getMensajeCorrecto() {
        return mensajeCorrecto;
    }

    public void setMensajeCorrecto(String mensajeCorrecto) {
        this.mensajeCorrecto = mensajeCorrecto;
    }
    
    
}
