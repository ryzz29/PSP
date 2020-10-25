import java.util.ArrayList;

public class Algoritmos {
    ArrayList<Proceso> listaProcesos = new ArrayList<>();
    ArrayList<Proceso> cola = new ArrayList<>();
    ArrayList<Proceso> colaDone = new ArrayList<>();
    int done = 0;

    boolean doneTotal = false;
    int cicloCpu = 0;


    /*
    Simplemente generamos una lista de procesos que se corresponde con la del ejercicio de clase
     */
    public void generarListaProcesosA(){
        Proceso process = new Proceso('A',0,5);
        listaProcesos.add(process);
        process = new Proceso('B',2,4);
        listaProcesos.add(process);
        process = new Proceso('C',3,3);
        listaProcesos.add(process);
        process = new Proceso('D',5,2);
        listaProcesos.add(process);
        process = new Proceso('E',6,3);
        listaProcesos.add(process);
    }

    /*
    Simplemente generamos una lista de procesos que se corresponde con la del ejemplo de repaso
     */
    public void generarListaProcesosB() {
        Proceso process = new Proceso('A', 0, 3);
        listaProcesos.add(process);
        process = new Proceso('B', 1, 4);
        listaProcesos.add(process);
        process = new Proceso('C', 3, 2);
        listaProcesos.add(process);
        process = new Proceso('D', 5, 3);
        listaProcesos.add(process);
        process = new Proceso('E', 6, 4);
        listaProcesos.add(process);
    }

    /*
    Simplemente generamos una lista de procesos que se corresponde con la de los apuntes
     */
    public void generarListaProcesosC(){
        Proceso process = new Proceso('A', 0, 3);
        listaProcesos.add(process);
        process = new Proceso('B', 2, 6);
        listaProcesos.add(process);
        process = new Proceso('C', 4, 4);
        listaProcesos.add(process);
        process = new Proceso('D', 6, 5);
        listaProcesos.add(process);
        process = new Proceso('E', 8, 2);
        listaProcesos.add(process);
    }

    /*
    Comprobamos en cada ciclo si un proceso ha llegado, varía si se trata del ciclo 0
     */
    public void compruebaSiEntraUnProceso(){ //Para el ciclo 0, añadimos el proceso correspondiente
        for (Proceso process: listaProcesos){
            if (process.tiempoDeLLegada==cicloCpu & cicloCpu==0){
                cola.add(listaProcesos.get((listaProcesos.indexOf(process))));
            } //En caso de añadir después del ciclo 0, tomamos en cuenta que debe añadirse justo antes de que acabe el ciclo anterior (por eso +1)
            if (process.tiempoDeLLegada==(cicloCpu+1) & process != cola.get(0)){
                cola.add(listaProcesos.get((listaProcesos.indexOf(process))));
            }
        }
    }

    /*
    Imprime por pantalla la simulación de la ejecución del proceso, acemás utiliza Thread.sleep para darle realismo
     */
    public void imprimeEnPantalla(){
        try {
            Thread.sleep(500);    //Con un pequeño delay de 500 milisegundos provocamos que parezca que el ordenador está pensando
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (cola.get(0).rafaga == 0) {      //Si el proceso acaba, añadimos el --Done
            System.out.println("Ciclo " + (cicloCpu+1) + " Proceso [id=" + cola.get(0).nombreProceso + ", ráfaga pendiente=" + cola.get(0).rafaga + "] -- Done");
            cola.get(0).cicloFinal=cicloCpu+1; //añadimos el ciclo en el que termina para usarlo posteriormente
            done++;                            //Sumamos 1 al número de procesos terminados
        } else {                            //Si el proceso no acaba, lo hacemos sin --Done
            System.out.println("Ciclo " + (cicloCpu+1) + " Proceso [id=" + cola.get(0).nombreProceso + ", ráfaga pendiente=" + cola.get(0).rafaga + "]");
        }
    }

    /*
    Simula el Fifo
     */
    public void Fifo(){
        while (!doneTotal){                 //Mientras no hayamos terminado
            compruebaSiEntraUnProceso();    //llamamos a la comprobación de entrada de procesos
            cola.get(0).rafaga-=1;          //Restamos uno a la ráfaga dado que "se ejecuta"
            imprimeEnPantalla();            //Lo imprimimos en pantalla y además añadimos el ciclo en el que termina para usarlo posteriormente (si termina)
            if (cola.get(0).rafaga==0){     //Revisamos si ha terminado
                colaDone.add(cola.get(0));
                cola.remove(0);       //Si ha terminado lo sacamos de ejecución
            }
            cicloCpu++;                     //incrementamos el ciclo de la cpu y estamos listos
            comprobarDoneTotal();           //comprobamos si ha terminado, para cerrar el bucle + delay de 0.5 segundos
        }
        calcular(colaDone);                 //Hacemos los cálculos de índices de penalización, utilizando la lista de los procesos terminados
    }

    /*
    Simula el Round Robin
     */
    public void RR(int quantum){
        int q = 0;                          //El quantum comienza en 0
        int maxq = quantum;                 //El quantum máximo al que podemos llegar, dado por el usuario

        while (!doneTotal){                 //Mientras no hayamos terminado
            compruebaSiEntraUnProceso();    //llamamos a la comprobación de entrada de procesos

            cola.get(0).rafaga-=1;          //Restamos uno a la ráfaga dado que "se ejecuta"
            imprimeEnPantalla();            //Lo imprimimos en pantalla y además añadimos el ciclo en el que termina para usarlo posteriormente (si termina)
            q++;                            //Incrementamos el quantum

            if (cola.get(0).rafaga==0){     //Revisamos si ha terminado
                colaDone.add(cola.get(0));
                cola.remove(0);       //Si ha terminado lo sacamos de ejecución
                q = 0;                      //y además reiniciamos el quantum
            }

            if (q == maxq){                 //Si el quantum ha llegado al máximo
                cola.add(cola.get(0));      //Colocamos el primero en el último puesto
                cola.remove(0);       //Y lo eliminamos del primer puesto
                q = 0;                      //Reiniciamos el quantum
            }

            cicloCpu++;                     //Incrementamos el ciclo de la cpu
            comprobarDoneTotal();           //comprobamos si ha terminado, para cerrar el bucle + delay de 0.5 segundos
        }
        calcular(colaDone);                 //Hacemos los cálculos de índices de penalización, utilizando la lista de los procesos terminados
    }

    /*
    Simula el SJF
     */
    public void SJF(){
        boolean cambiar = false;            //Generamos un boolean que nos dirá si hay que cambiar el proceso por otro más corto

        while (!doneTotal){                 //Mientras no hayamos terminado
            Proceso masCorto = new Proceso('Z',999,999); //Generamos un proceso brutalmente largo, que nos ayudará a comparar (se genera en cada while)
            compruebaSiEntraUnProceso();    //llamamos a la comprobación de entrada de procesos

            cola.get(0).rafaga-=1;          //Restamos uno a la ráfaga dado que "se ejecuta"
            imprimeEnPantalla();            //Lo imprimimos en pantalla y además añadimos el ciclo en el que termina para usarlo posteriormente (si termina)

            if (cola.get(0).rafaga==0){     //Si acaba su ráfaga...
                colaDone.add(cola.get(0));
                cola.remove(0);       //Lo eliminamos del primer puesto de la cola
                for (Proceso process: cola){// Usamos un bucle para comparar y ver cual debe colocarse el primero
                    if (process.rafaga<cola.get(0).rafaga & cola.size()>1){ //Si el proceso actual, es más corto que el primero y la cola tiene más de 1 proceso...
                        if (masCorto.rafaga>process.rafaga & masCorto != null) {//Si masCorto tiene una rafaga menor que el proceso actual...
                            masCorto = process; //Igualamos mascorto al proceso actual (por eso utilizabamos uno al principio con una rafaga brutal)
                            cambiar = true;     //Decimos que debe cambiar
                        }
                    }
                }
            }

            if (cambiar){                                       //Si debe cambiar...
                cola.remove(cola.get(cola.indexOf(masCorto)));  //Eliminamos al "masCorto" de la lista
                cola.add(0, masCorto);                    //Y lo colocamos en primera posición
                cambiar = false;                                //Ya no debe cambiar
            }
            cicloCpu++;                                         //Incrementamos el ciclo de la cpu
            comprobarDoneTotal();           //comprobamos si ha terminado, para cerrar el bucle + delay de 0.5 segundos
        }
        calcular(colaDone);                 //Hacemos los cálculos de índices de penalización, utilizando la lista de los procesos terminados
    }

    /*
    Simula el SRT
     */
    public void SRT(){
        boolean cambiar = false;            //Generamos un boolean que nos dirá si hay que cambiar el proceso por otro más corto

        while (!doneTotal){                 //Mientras no hayamos terminado
            Proceso masCorto = new Proceso('Z',999,999); //Generamos un proceso brutalmente largo, que nos ayudará a comparar (se genera en cada while)
            compruebaSiEntraUnProceso();    //llamamos a la comprobación de entrada de procesos

            if (cicloCpu>=1){               //Si el ciclo de la cpu es 1 o mayor... [He dado por hecho que no entran 2 en el ciclo 0...]
                for (Proceso process: cola){    //Recorremos la cola
                    if (process.rafaga<cola.get(0).rafaga & cola.size()>1){ //Si el proceso actual, es más corto que el primero y la cola tiene más de 1 proceso...
                        if (masCorto.rafaga>process.rafaga & masCorto != null) {//Si masCorto tiene una rafaga menor que el proceso actual...
                            masCorto = process; //Igualamos mascorto al proceso actual (por eso utilizabamos uno al principio con una rafaga brutal)
                            cambiar = true;     //Decimos que debe cambiar
                       }
                    }
                }
            }

            if (cambiar){                       //Si debe cambiar...
                cola.remove(cola.get(cola.indexOf(masCorto)));  //Eliminamos al "masCorto" de la lista
                cola.add(0, masCorto);                    //Y lo colocamos en primera posición
                cambiar = false;                                //Ya no debe cambiar
            }

            cola.get(0).rafaga-=1;          //Restamos uno a la ráfaga dado que "se ejecuta"
            imprimeEnPantalla();            //Lo imprimimos en pantalla y además añadimos el ciclo en el que termina para usarlo posteriormente (si termina)

            if (cola.get(0).rafaga==0){     //Si acaba su ráfaga...
                colaDone.add(cola.get(0));
                cola.remove(0);       //Lo eliminamos del primer puesto de la cola
            }

            cicloCpu++;                                         //Incrementamos el ciclo de la cpu
            comprobarDoneTotal();           //comprobamos si ha terminado, para cerrar el bucle + delay de 0.5 segundos
        }
        calcular(colaDone);                 //Hacemos los cálculos de índices de penalización, utilizando la lista de los procesos terminados
    }

    /*
    Simplemente comprobamos si la cola ha terminado la misma cantidad de procesos, que los que hemos suministrado
     */
    public void comprobarDoneTotal(){
        if (done==listaProcesos.size()){
            doneTotal = true;
            System.out.println("/////////////// Totalmente donete mai frendo ///////////////////////////////////////////////////////////////////////");
        }
    }

    /*
    Calcula los índices de penalización de cada proceso dentro del algoritmo utilizado y después hace la media para concocer
    el índice de penalización del algoritmo.
     */
    public void calcular(ArrayList<Proceso> lista){
        float resultadoFinal = 0;
        int i = 0;

        for (Proceso process: lista){ //Recorremos la lista
            try {
                Thread.sleep(500);    //Con un pequeño delay de 500 milisegundos provocamos que parezca que el ordenador está pensando
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            float resultado =  ((float)((process.cicloFinal)- process.tiempoDeLLegada)/process.rafagaInicial); //hacemos el cálculo correspondiente
            System.out.println("El índice de penalización de " + process.nombreProceso + " es de " + resultado); //Imprimimos el resultado
            resultadoFinal += resultado;    //Acumulamos el resultado para hacer la media
            i++;                            //Contamos entre cuanto tendremos que dividir
        }
        resultadoFinal = resultadoFinal / i; //Finalizamos el cálculo
        System.out.println("El índice de penalización de este algoritmo es de " + resultadoFinal); //Imprimimos por pantalla
    }
}