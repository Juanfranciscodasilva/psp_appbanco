package servidor;

import Clases.CuentaBancaria;
import Clases.Hilo;
import Clases.Usuario;

import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Main {
    
    public static final int PORT = 54321;

    //Lista usuarios registrados
    public static List<Usuario> usuarios = new ArrayList<>();
    public static List<CuentaBancaria> cuentasBancarias = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(PORT);
        Socket socket = new Socket();

        //inserta un usuario pre-cargado para evitar la parte del registro
        insertarDatosEjemplo();
        while (true) {
            socket = serverSocket.accept();
            Hilo hilo = new Hilo(socket,usuarios,cuentasBancarias);
            hilo.start();
        }
    }

    public static void insertarDatosEjemplo(){
        Usuario usu = new Usuario();
        usu.setDni("58046446N");
        //password 12345Abcde%
        usu.setPassword("qqca8eKud9v/L7WPkZAdv9VvfiMs1sLzoTHyouFlT9c=");
        usu.setNombre("Juan Francisco");
        usu.setApellidos("Da Silva García");
        usu.setEmail("juanfrancisco.dasilva@ikasle.egibide.org");
        usu.setEdad(21);
        usu.setNuevo(false);
        usuarios.add(usu);
        CuentaBancaria cuenta = new CuentaBancaria();;
        cuenta.setNumeroCuenta("000000000000");
        cuenta.setSaldo(100);
        cuenta.setDniPropietario(usu.getDni());
        cuentasBancarias.add(cuenta);

        Usuario usu2 = new Usuario();
        usu.setDni("58046447J");
        //password 12345Abcde%
        usu.setPassword("qqca8eKud9v/L7WPkZAdv9VvfiMs1sLzoTHyouFlT9c=");
        usu.setNombre("Test");
        usu.setApellidos("test");
        usu.setEmail("test@gmail.com");
        usu.setEdad(21);
        usu.setNuevo(false);
        usuarios.add(usu2);
        CuentaBancaria cuenta2 = new CuentaBancaria();;
        cuenta.setNumeroCuenta("000000000001");
        cuenta.setSaldo(100);
        cuenta.setDniPropietario(usu2.getDni());
        cuentasBancarias.add(cuenta2);
    }
    
}
