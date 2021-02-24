package Main;
import DAO.CompraDAO;
import DAO.EmpleadoDAO;
import DAO.ProductoDAO;
import Utils.EmailSender;
import Utils.Turbofecheador;
import Utils.Turbolector;
import models.Empleado;

import java.io.*;
import java.net.*;

/**
 * Cada uno de los hilos que van a estar en contacto con los clientes (cajeros)
 */
public class HiloServidor extends Thread {

    //Estados
    EmpleadoDAO empleadoDAO;
    Turbofecheador fecheador;
    CompraDAO comprador;
    Empleado empleado = null;
    EmailSender emaileador;
    ProductoDAO producteador;
    Turbolector turbolector;

    String emailProveedor = "";

    private static ObjectOutputStream flujoSalida = null;

    boolean exit = false;

    int id = 0;

    private Socket cliente;

    //Constructor en el que inicializamos aquello que necesitamos inicializar para trabajar
    public HiloServidor(Socket cliente){
        this.cliente = cliente;
        empleadoDAO = new EmpleadoDAO();
        fecheador = new Turbofecheador();
        comprador = new CompraDAO();
        emaileador = new EmailSender();
        producteador = new ProductoDAO();
        turbolector = new Turbolector();
    }

    /**
     * Método principal que se encarga de interactuar con los clientes (cajeros)
     */
    @Override
    public void run() {
        //Cargamos el email del proveedor al inicio como requiere el ejercicio
        emailProveedor = turbolector.generarCorreoProveedor();

        try {
            // CREO FLUJO DE ENTRADA DEL CLIENTE
            InputStream entrada = null;

            entrada = cliente.getInputStream();
            DataInputStream flujoEntrada = new DataInputStream(entrada);

            // EL CLIENTE ME ENVIA UN MENSAJE
            String incomingData = flujoEntrada.readUTF();
            System.out.println("Recibiendo del CLIENTE: \n\t" + incomingData);

            //Troceamos el string que recibimos y comprobamos si quiere hacer Login, en cuyo caso nos guardamos la id para comprobarlo en la BBDD
            String[] parts = incomingData.split(";");
            if (parts[0].equalsIgnoreCase("login")){
                id = Integer.parseInt(parts[1]);
            }

            // CREO FLUJO DE SALIDA AL CLIENTE
            OutputStream salida = null;
            salida = cliente.getOutputStream();
            System.out.println("Enviando Empleado!");
            flujoSalida = new ObjectOutputStream(salida);

            //Comprobamos el Login y le enviamos la respuesta (que será null si no existe un cajero con dicha id)
            empleado = empleadoDAO.comprobarLogin(id);
            flujoSalida.writeObject(empleado);

            //Si el empleado existe, actualizamos su última conexión (dado que habrá hecho login)
            if (empleado != null){
                empleadoDAO.actualizarUltimaSesion(empleado.getID_Empleado(), fecheador.generarFechaSql());

                //Utilizamos un bucle para recibir los mensajes del cajero y reaccionar ante lo que nos envíe
                while (!exit) {
                    incomingData = flujoEntrada.readUTF();
                    String[] parts2 = incomingData.split(";");
                    switch (parts2[0]) {
                        case "Cobro":
                            System.out.println("Cobrar");
                            Cobrar(parts2);
                            break;
                        case "Caja":
                            System.out.println("Caja");
                            obtenerCajaDelDia(parts2);
                            break;
                        case "Exit":
                            exit = true;
                    }
                }
            }

            // CERRAR STREAMS Y SOCKETS
            entrada.close();
            flujoEntrada.close();
            salida.close();
            flujoSalida.close();
            cliente.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que nos permite lanzar a la base de datos un insert con la compra que el cajero realiza.
     * Además nos lanzará un boolean de vuelta que nos informa de que hay (o no) que reponer stock, en cuyo caso mandaremos un email notificándolo
     * @param parts2
     * @throws Exception
     */
    private void Cobrar(String[] parts2) throws Exception {
        comprador.realizarCompra(Integer.parseInt(parts2[1]),empleado.getID_Empleado(),Integer.parseInt(parts2[2]),Integer.parseInt(parts2[3]), fecheador.generarFechaSql());
        boolean necesitamosStock = producteador.actualizarStock(Integer.parseInt(parts2[1]),Integer.parseInt(parts2[2]));
        if (necesitamosStock){
            emaileador.sendEmail(parts2[4], fecheador.generarHora(), fecheador.generarFechaString(), emailProveedor, producteador.checkPrecioProveedor(Integer.parseInt(parts2[1])));
        }
    }

    /**
     * Método que nos permite obtener la cantidad de dinero que se ha hecho de caja.
     * Diferenciará el mensaje del cliente según si quiere la cantidad total, o la cantidad parcial ganada por dicha caja.
     * @param parts2
     * @throws IOException
     */
    private void obtenerCajaDelDia(String[] parts2) throws IOException {
        if (parts2[1].equalsIgnoreCase("unica")){
            int total = comprador.obtenerCajaDelDiaTotal(fecheador.generarFechaSql());
            System.out.println("Dinero total:");
            System.out.println(total + "€");
            flujoSalida.writeObject(String.valueOf(total));
        } else {
            int parcial = comprador.obtenerCajaDelDiaParcial(Integer.parseInt(parts2[2]), fecheador.generarFechaSql());
            System.out.println("Dinero total de la caja requerida:");
            System.out.println(parcial + "€");
            flujoSalida.writeObject(String.valueOf(parcial));
        }
    }

}