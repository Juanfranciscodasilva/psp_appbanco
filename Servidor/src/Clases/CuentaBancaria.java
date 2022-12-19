package Clases;

public class CuentaBancaria {

    private String dniPropietario;

    private String numeroCuenta;

    private int saldo;

    public CuentaBancaria(){

    }

    public String getDniPropietario() {
        return dniPropietario;
    }

    public void setDniPropietario(String dniPropietario) {
        this.dniPropietario = dniPropietario;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
}
