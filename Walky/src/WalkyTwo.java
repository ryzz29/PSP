import java.io.IOException;
import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class WalkyTwo {
    //Boolean preparado para cortar el bucle del menú
    static boolean wannaContinue = true;
    //Scanner utilizado para pillar los inputs de consola
    static Scanner sn = new Scanner(System.in);
    //Inicializamos la InetAddress que utilizaremos para mandar los paquetes
    static InetAddress destino = null;
    //Puerto donde trabajaremos
    static int port = 1;

    public static void main(String[] argv) throws Exception {
        //Utilizamos el puerto donde vamos a mandarlo
        DatagramSocket socket = new DatagramSocket(2);
        mensajesEtapaUno();
        switchEtapaUno();
        //Bucle para el menú siguiendo la descripción del pdf del ejercicio
        while (wannaContinue) {
            mensajesEtapaDos();
            switchEtapaDos(socket);
        }
    }

    /**
     * Método que nos imprime por pantalla la información relevante sobre el primer switch de opciones
     */
    public static void mensajesEtapaUno(){
        System.out.println("Ahora es necesario que selecciones la frecuencia de comunicación");
        System.out.println("Escribe el número de una de las siguientes opciones para elegir la opción");
        System.out.println("1. Utilizar frecuencia por defecto (local)");
        System.out.println("2. Utilizar una frecuencia disinta ");
    }

    /**
     * Método que ofrece el primer switch de opciones sobre la "frecuencia" en que trabajeremos
     */
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

    /**
     * Método que nos crea una InetAddress utilizando un string con el formato adecuado
     */
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

    /**
     * Método que nos imprime por pantalla la información relevante sobre el segundo switch de opciones
     */
    public static void mensajesEtapaDos(){
        System.out.println("1. Hablar");
        System.out.println("2. Recibir");
        System.out.println("3. Cerrar conexión y salir");

    }

    /**
     * Método que ofrece el segundo switch de opciones, en este caso sobre hablar, escuchar o salir
     */
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

    /**
     * método que utiliza un DatagramSocket dado se prepara para recibir un DatagramPacket con el String de información de la función hablar de otro walky
     * @param socket
     * @throws IOException
     */
    public static void escuchar(DatagramSocket socket) throws IOException {
        System.out.println(" -  esperando mensaje ... -");
        byte[] bufer = new byte[10240];//bufer para recibir el datagrama
        DatagramPacket recibo = new DatagramPacket(bufer, bufer.length, destino, port);
        socket.receive(recibo);//recibo datagrama
        int bytesRec = recibo.getLength();//obtengo numero de bytes
        String paquete = new String(recibo.getData(),0 , bytesRec);//obtengo String

        //VISUALIZO INFORMACIÓN
        System.out.println(" *GHGHHGHGHGHH*");
        System.out.println(paquete.trim()); //String
        System.out.println(" *GHGHHGHGHGHH* \n");
    }

    /**
     * método que utiliza un DatagramSocket dado que se a partir de un input de usuario envía un DatagramPacket con un string
     * @param socket
     * @throws IOException
     */
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