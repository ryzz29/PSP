import java.io.IOException;
import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class WalkyTwo {
    static boolean wannaContinue = true;
    static Scanner sn = new Scanner(System.in);
    static InetAddress destino = null;
    static int port = 1;

    public static void main(String[] argv) throws Exception {
        DatagramSocket socket = new DatagramSocket(2);
        mensajesEtapaUno();
        switchEtapaUno();
        while (wannaContinue) {
            mensajesEtapaDos();
            switchEtapaDos(socket);
        }
    }

    public static void mensajesEtapaUno(){
        System.out.println("Ahora es necesario que selecciones la frecuencia de comunicación");
        System.out.println("Escribe el número de una de las siguientes opciones para elegir la opción");
        System.out.println("1. Utilizar frecuencia por defecto (local)");
        System.out.println("2. Utilizar una frecuencia disinta ");
    }

    public static void switchEtapaUno(){
        try {
            int opcionNumero = sn.nextInt();
            switch (opcionNumero) {
                case 1:
                    System.out.println("Utilizando frecuencia por defecto (local)");
                    destino = InetAddress.getLocalHost();
                    break;
                case 2:
                    System.out.println("Utilizando frecuencia distinta");
                    creaAddress();
                    break;
            }
        } catch (InputMismatchException | UnknownHostException e){
            System.out.println("No has introducido un número de las opciones correspondientes...");
            sn.next();
            switchEtapaUno();

        }
    }

    public static void creaAddress() {
        try {
            destino = InetAddress.getByName(sn.next());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if (destino == null){
            creaAddress();
        }
    }

    public static void mensajesEtapaDos(){
        System.out.println("1. Hablar");
        System.out.println("2. Recibir");
        System.out.println("3. Cerrar conexión y salir");

    }

    public static void switchEtapaDos(DatagramSocket socket){
        try {
            int opcionNumero = sn.nextInt();
            switch (opcionNumero) {
                case 1:
                    hablar(socket);
                    break;
                case 2:
                    escuchar(socket);
                    break;
                case 3:
                    socket.close();
                    wannaContinue = false;
                    break;
            }
        } catch (InputMismatchException e){
            System.out.println("No has introducido un número de las opciones correspondientes...");
            sn.next();
            switchEtapaDos(socket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void escuchar(DatagramSocket socket) throws IOException {
        System.out.println(" - esperando -");
        byte[] bufer = new byte[10240];//bufer para recibir el datagrama
        DatagramPacket recibo = new DatagramPacket(bufer, bufer.length, destino, port);
        socket.receive(recibo);//recibo datagrama
        int bytesRec = recibo.getLength();//obtengo numero de bytes
        String paquete = new String(recibo.getData(),0 , bytesRec);//obtengo String

        //VISUALIZO INFORMACIÓN
        System.out.println(" *GHGHHGHGHGHH*");
        System.out.println(paquete.trim());
        System.out.println(" *GHGHHGHGHGHH* \n");
    }

    public static void hablar(DatagramSocket socket) throws IOException {
        System.out.println(" *GHGHHGHGHGHH* \n Canal abierto, puedes hablar \n *GHGHHGHGHGHH*");

        byte[] mensaje = new byte[10240];
        Scanner sn = new Scanner(System.in);
        String premensaje = sn.nextLine();
        mensaje = premensaje.getBytes(); //codifico String a bytes

        //CONSTRUYO EL DATAGRAMA A ENVIAR
        DatagramPacket envio = new DatagramPacket(mensaje, mensaje.length, destino, port);

        //ENVIO DATAGRAMA
        socket.send(envio);
    }

}