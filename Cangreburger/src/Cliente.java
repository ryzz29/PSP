import java.util.Random;

public class Cliente extends Thread{
    private Bandeja bandeja;
    private int idCliente;

    /**
     * Generamos un cliente, con un identificador y le asignamos la bandeja de la que va a comer
     * @param id
     * @param bandeja
     */
    public Cliente (int id, Bandeja bandeja){
        this.idCliente = id;
        this.bandeja = bandeja;
    }

    public synchronized void run() {
        while (true) { //Bucle infinito nonstop
            Random random = new Random();
            try {
                //Sleep después de comer dado que tardan en volver a tener hambre
                int tiempo = random.nextInt(2000) + 1000;
                bandeja.comer(idCliente);
                sleep(tiempo);
                System.out.println("El cliente " + idCliente + " ha tardado " + tiempo + " milisegundos en volver a tener hambre");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
