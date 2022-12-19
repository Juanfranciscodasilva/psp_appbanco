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

        while (true) {
            socket = serverSocket.accept();
            Hilo hilo = new Hilo(socket,usuarios,cuentasBancarias);
            hilo.start();
        }
    }
    
}
