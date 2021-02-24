package Main;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Clase servidor a la que se conectarán los cajeros y que produce un hilo por cada conexión que reciba
 */
public class Servidor {

    public static void main(String args[]) throws IOException {

        //Preparamos el socket y el servidor
        ServerSocket servidor;
        servidor = new ServerSocket(6001);

        //Avisamos al usuario
        System.out.println("Servidor iniciado...");

        //BUcle para generar hilos conforme recibamos conexiones
        while (true) {
            Socket cliente = new Socket();
            cliente=servidor.accept();//esperando cliente
            HiloServidor hilo = new HiloServidor(cliente);
            hilo.start(); //Se atiende al cliente

        }
    }
}