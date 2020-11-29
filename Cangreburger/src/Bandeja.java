public class Bandeja {
    private int nHamburguesas;

    public Bandeja() {
        nHamburguesas = 0;
    }

    /**
     * Aumenta en 1 el número de hamburguesas y notifica a los clientes que pueden acudir a por ella
     * @param idHilo
     */
    public synchronized void cocinar(int idHilo) {
        nHamburguesas++;
        System.out.println("El cocinero "+ idHilo +" ha añadido una cangreburguer, por lo que quedan " + nHamburguesas + " cangreburguer en la bandeja");
        notify();
    }

    /**
     * Primero comprueba si hay hamburguesas, en cuyo caso disminuye en 1 el número de hamburguesas
     * en caso contrario, los clientes esperarán a ser avisados por el olor de una cangreburguer recién hecha
     * @param idHilo
     */
    public synchronized void comer(int idHilo) {
        comprobarBandeja();
        nHamburguesas--;
        System.out.println("El cliente "+ idHilo +" ha cogido una cangreburguer, por lo que quedan " + nHamburguesas + " cangreburguer en la bandeja");
    }

    /**
     * Comprueba si la bandeja está vacía y por tanto no hay hamburguesas, en cuyo caso pondrá al cliente a esperar
     */
    public synchronized void comprobarBandeja(){
        if (nHamburguesas<=0){ //En lugar de usar == 0 he usado <= 0 para intentar que funcione mejor cuando meto hilos a saco y controla un poco el fallo
                                //pero no por ello consigue llegar a funcionar bien
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

