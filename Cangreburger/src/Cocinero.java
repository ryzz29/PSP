import java.util.Random;

public class Cocinero extends Thread {
    private Bandeja bandeja;
    private int idCocinero;

    /**
     * Generamos un cocinero, con un identificador y le asignamos la bandeja en la que va a poner las cangreburguers calentitas
     * @param id
     * @param bandeja
     */
    public Cocinero (int id, Bandeja bandeja){
        this.idCocinero = id;
        this.bandeja = bandeja;
    }

    public synchronized void run() {
        while (true) { //Bucle infinito nonstop
            Random random = new Random();
            try {
                //Sleep antes de cocinar dado que tardan en cocinarla
                int tiempo = random.nextInt(2000) + 1000;
                System.out.println("El cocinero " + idCocinero + " ha tardado " + tiempo + " milisegundos en hacer una cangreburguer");
                sleep(tiempo);
                bandeja.cocinar(idCocinero);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
