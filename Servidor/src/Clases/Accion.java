package Clases;

import java.io.Serializable;

public class Accion implements Serializable {

    private String accion;

    private String cuentaBancaria;

    private String cuentaBancaria2;

    private int importe;

    public Accion() {
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getCuentaBancaria() {
        return cuentaBancaria;
    }

    public void setCuentaBancaria(String cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }

    public String getCuentaBancaria2() {
        return cuentaBancaria2;
    }

    public void setCuentaBancaria2(String cuentaBancaria2) {
        this.cuentaBancaria2 = cuentaBancaria2;
    }

    public int getImporte() {
        return importe;
    }

    public void setImporte(int importe) {
        this.importe = importe;
    }
}
