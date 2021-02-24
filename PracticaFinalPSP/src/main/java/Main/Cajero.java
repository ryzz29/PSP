package Main;
import models.Empleado;

import java.io.*;
import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Cajero {

    //Estados
    static Scanner sn = new Scanner(System.in);
    static boolean wannacontinue = true;
    static int caja = 2;
    static int contador = 0;
    private static DataOutputStream flujoSalida = null;
    private static ObjectInputStream flujoEntrada = null;

    /**
     * Método main de la clase Cajero
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        //Preparamos la conexión
        String Host = "localhost";
        int Puerto = 6001;//puerto remoto

        System.out.println("Bienvenido al panel de control de Cajeros");
        Socket Cliente = new Socket(Host, Puerto);

        // CREO FLUJO DE SALIDA AL SERVIDOR
        flujoSalida = new DataOutputStream(Cliente.getOutputStream());

        // ENVIO UN LOGIN AL SERVIDOR
        flujoSalida.writeUTF(login());

        // CREO FLUJO DE ENTRADA AL SERVIDOR (Para recibir el objeto empleado)
        System.out.println("Recibiendo datos del servidor...");
        flujoEntrada = new ObjectInputStream(Cliente.getInputStream());

        Empleado empleado = (Empleado) flujoEntrada.readObject();

        //Comprobamos si ha sido un login satisfactorio
        if (empleado != null) {
            System.out.println("Bienvenido cajero nº: " + empleado.getID_Empleado());
            System.out.println("Su última conexión fue: " + empleado.getUltima_Sesion());

            //En ese caso comenzamos con el bucle de los menús utilizando un boolean para salir del mismo si el usuario utiliza la opción salir
            while (wannacontinue) {
                mensajesEtapaDos();
                switchEtapaDos();
            }
        } else {
            System.out.println("Login incorrecto");
        }

        // CERRAR STREAMS Y SOCKETS
        flujoSalida.writeUTF("Exit;");
        flujoEntrada.close();
        flujoSalida.close();
        Cliente.close();

    }// fin de main


    /**
     * método que nos permite introducir un input por consola del intento de login, devuelve el string comprobando antes si tiene el formato correcto
     * @return
     */
    public static String login(){
        String entrada = "";
        while (!comprobacionErroresLogin(entrada)) {
            System.out.println("Para continuar escriba sus datos de login:");
            entrada = sn.nextLine();
        }
        return entrada;
    }


    /**
     * Método que comprueba si el intento de login es correcto, tenemos un contador para que cuando se inicia el bucle en el método anterior
     * no devuelva por consola un mensaje de error (que lo produciría la primera vez que se entre en el método)
     * El boolean nos permite saber si ha pasado las comprobaciones (true) o si no las ha pasado (false)
     * @param entrada
     * @return
     */
    public static boolean comprobacionErroresLogin(String entrada) {
        boolean passed = false;
        String[] parts = entrada.split(";");
        if (parts[0].equalsIgnoreCase("login")) {
            try{
                Integer.parseInt(parts[1]);
                passed = true;
            } catch (InputMismatchException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Fallo al final");
            }
        } else {
            if (contador == 1){
            System.out.println("Fallo al comienzo");
            } else{
               contador ++;
            }
        }
        return passed;
    }

    /**
     * Método que nos imprime por pantalla la información relevante sobre el segundo switch de opciones
     */
    public static void mensajesEtapaDos(){
        System.out.println("1. Cobrar compra");
        System.out.println("2. Obtener la caja del día");
        System.out.println("3. Salir");

    }

    /**
     * Método que contiene el funcionamiento del segundo switch de opciones
     */
    public static void switchEtapaDos(){
        try {
            int opcionNumero = sn.nextInt();
            switch (opcionNumero) {
                case 1:
                    mensajesCobrarCompra();
                    switchCobrarCompra();
                    break;
                case 2:
                    obtenerCajaDelDia();
                    break;
                case 3:
                    //El boolean que nos permite salir del bucle de ejecución del menú
                    wannacontinue = false;
                    System.out.println("Hasta pronto");
                    break;
                default:
                    mensajesEtapaDos();
                    switchEtapaDos();
            }
        } catch (InputMismatchException e){
            System.out.println("No has introducido un número de las opciones correspondientes...");
            sn.next();
            mensajesEtapaDos();
            switchEtapaDos();
        }
    }

    /**
     * Método que nos imprime por pantalla la información relevante sobre el switch de opciones de productos
     */
    private static void mensajesCobrarCompra() {
        System.out.println("1. Hamburguesa");
        System.out.println("2. Costillas");
        System.out.println("3. Croquetas");
        System.out.println("4. Lasaña");
        System.out.println("5. Salir");
    }

    /**
     * Método que contiene el funcionamiento  del switch de opciones de productos
     */
    private static void switchCobrarCompra() {
        try {
            int opcionNumero = sn.nextInt();
            switch (opcionNumero) {
                case 1:
                    cobrarCompra("Hamburguesas",1);
                    mensajesCobrarCompra();
                    switchCobrarCompra();
                    break;
                case 2:
                    cobrarCompra("Costillas",2);
                    mensajesCobrarCompra();
                    switchCobrarCompra();
                    break;
                case 3:
                    cobrarCompra("Croquetas",3);
                    mensajesCobrarCompra();
                    switchCobrarCompra();
                    break;
                case 4:
                    cobrarCompra("Lasañas",4);
                    mensajesCobrarCompra();
                    switchCobrarCompra();
                    break;
                case 5:
                    mensajesEtapaDos();
                    switchEtapaDos();
                    break;
                default:
                    mensajesCobrarCompra();
                    switchCobrarCompra();
                    break;
            }
        } catch (InputMismatchException e){
            System.out.println("No has introducido un número de las opciones correspondientes...");
            sn.next();
            mensajesEtapaDos();
            switchEtapaDos();
        }
    }

    /**
     * Método que nos imprime por pantalla la selección de la cantidad del producto seleccionado anteriormente
     * además mandará al método finalizarCompra un string preparado para utilizar dicho método
     * En caso de que la cantidad seleccionada sea 0 no pasará al siguiente paso y devolverá un aviso por consola
     * @param Producto
     * @param idProducto
     */
    private static void cobrarCompra(String Producto, int idProducto){

        System.out.println("¿Cuantas unidades quiere de " + Producto + "?");
        System.out.println("Recuerde que si desea hacer una devolución solo tiene que poner - delante de la cantidad del producto");
        System.out.println("Ejemplo: -2");
        System.out.println("En caso de que no desees hacer una compra de este artículo, tan solo escribe un 0");

        try {
            int opcionNumero = sn.nextInt();
            String compraFinal = idProducto + ";" + opcionNumero + ";" + caja + ";" + Producto;
            if (opcionNumero != 0) {
                System.out.println("Se van a comprar " + opcionNumero + " " + Producto + ".");
                finalizarCompra(compraFinal);
            } else {
                System.out.println("Se han seleccionando un total de 0 artículos. Por ello cancelamos el pedido.");
            }

        } catch (InputMismatchException | IOException e){
            System.out.println("No has introducido un número...");
            sn.next();
            cobrarCompra(Producto, idProducto);
        }
    }

    /**
     * Método que enviará al servidor un mensaje codificado con los datos necesarios para realizar la compra
     * (que en su mayor parte vienen dados del String introducido por parámetros en el método anterior)
     * @param compra
     * @throws IOException
     */
    private static void finalizarCompra(String compra) throws IOException {
        flujoSalida.writeUTF("Cobro;"+compra);
    }

    /**
     * Método que nos imprime por pantalla la selección de la caja del día
     * además contiene el funcionamiento del switch de opciones.
     * En caso de que se seleccione obtener la caja del día parcial o total
     * En ambos casos mandaremos al servidor un String con un mensaje codificado con al información necesaria para realizar la consulta adecuada
     * y esperaremos a recibir el objeto que contiene la información requerida
     */
    private static void obtenerCajaDelDia() {
        String incomingData;

        try {
            System.out.println("Vamos a obtener la caja del día");
            System.out.println("1. Caja del día de esta caja (" + caja + ")");
            System.out.println("2. Caja del día del total de cajas");
            System.out.println("3. Salir");

            int opcionNumero = sn.nextInt();

            switch (opcionNumero) {
                case 1:
                    flujoSalida.writeUTF("Caja;Unica;" + caja);
                    incomingData = (String) flujoEntrada.readObject();
                    System.out.println("Las ganancias totales de la caja nº" + caja + " han sido de " + incomingData + "€.");
                    break;
                case 2:
                    flujoSalida.writeUTF("Caja;Total;" + caja);
                    incomingData = (String) flujoEntrada.readObject();
                    System.out.println("Las ganancias totales todas las cajas han sido de " + incomingData + "€.");
                    break;
                case 3:
                    break;
                default:
                    obtenerCajaDelDia();
            }
        } catch (InputMismatchException | IOException | ClassNotFoundException e){
            System.out.println("No has introducido un número de las opciones correspondientes...");
            sn.next();
            obtenerCajaDelDia();
        }
    }

}