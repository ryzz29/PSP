import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static Scanner sn = new Scanner(System.in);
    private static Algoritmos algoritmo = new Algoritmos();
    private static boolean salir = false;
    private static boolean salir2 = false;

    public static void main(String Args[]){

        invocarEleccionLista();
        if (!salir) {
            invocarOpciones(); //Imprimimos por pantalla las opciones que tiene nuestro usuario
            invocarMenu();     //Comenzamos ya con el menú en cuanto a programación
            if (!salir2){
                System.out.println("FINISHED, un placer.");
            }
        }
    }

    /*
    Damos al usuario opciones sobre la lista de procesos que quiere usar y utilizamos una según su input
     */
    public static void invocarEleccionLista(){

        System.out.println("Los procesos se corresponden con // LETRA DEL PROCESO, TIEMPO DE LLEGADA, RÁFAGA //");
        System.out.println("1. Lista A: (lista de procesos que se corresponde con la del ejercicio de clase)");
        System.out.println("// A, 0, 5 // B, 2, 4 // C, 3, 3 // D, 5, 2 // E, 6, 3");
        System.out.println("2. Lista B: (lista de procesos que se corresponde con la del ejemplo de repaso)");
        System.out.println("// A, 0, 3 // B, 1, 4 // C, 3, 2 // D, 5, 3 // E, 6, 4");
        System.out.println("3. Lista C: (lista de procesos que se corresponde con la de los apuntes)");
        System.out.println("// A, 0, 3 // B, 2, 6 // C, 4, 4 // D, 6, 5 // E, 8, 2");
        System.out.println("0. Para salir");

        try {
            System.out.println("Para empezar elige una de las dos opciones anteriores de listas de procesos:");
            String opcionB = sn.next();

            int i = Integer.parseInt(opcionB);

            //Un switch sencillo, normal y corriente con sus opciones correspondientes a las de invocarOpciones()
            switch (i) {
                case 1:
                    algoritmo.generarListaProcesosA();
                    System.out.println("Usaremos la lista A");
                    break; // break es opcional

                case 2:
                    algoritmo.generarListaProcesosB();
                    System.out.println("Usaremos la lista B");
                    break; // break es opcional

                case 3:
                    algoritmo.generarListaProcesosC();
                    System.out.println("Usaremos la lista B");
                    break; // break es opcional

                case 0:
                    salir = true;
                    System.out.println("Bueno, nos vemos!");
                    break;

                default:
                    System.out.println("No has elegido ninguna de las opciones...");
                    invocarEleccionLista();
            }

        } catch (NumberFormatException e) {
            System.out.println("Debes insertar un número");
            invocarEleccionLista();
        }
    }

    /*
    Imprimimos por pantalla las opciones que tiene nuestro usuario
     */
    public static void invocarOpciones(){
        System.out.println("Te ofrezco las siguientes opciones:");
        System.out.println("1. Fifo");
        System.out.println("2. RR");
        System.out.println("3. SJF");
        System.out.println("4. SRT");
        System.out.println("5. Salir");
        System.out.println("0. Repetir las opciones");
    }

    /*
    Se trata de un menú de opciones imprimido por pantalla para la selección de algoritmos (mediante input del usuario)
     */
    public static void invocarMenu(){

        try {
            //Pedimos un input del usuario
            System.out.println("Escribe el número de una de las opciones anteriores para continuar");
            String opcion = sn.next();

            //Pasamos el string a int dado que estamos seguros de que es un número porque comprobamos su numeralidad
            int i = Integer.parseInt(opcion);

            //Un switch sencillo, normal y corriente con sus opciones correspondientes a las de invocarOpciones()
            switch (i) {
                case 0:
                    invocarOpciones();
                    invocarMenu();
                    break; // break es opcional

                case 1:
                    algoritmo.Fifo();
                    break; // break es opcional

                case 2:
                    try {
                        System.out.println("Escribe el número que quieres usar como Quantum");
                        String opcionNum = sn.next();
                        int opcionNumNum = Integer.parseInt(opcionNum);

                        if (opcionNumNum != 0) {
                            algoritmo.RR(opcionNumNum);

                        } else {
                            System.out.println("0 no es un número lógico para este caso, deberías replanteartelo...");
                            invocarOpciones();
                            invocarMenu();
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Debes insertar un número... te devuelvo a la seleción de algoritmo...");
                        invocarOpciones();
                        invocarMenu();
                    }
                    break; // break es opcional

                case 3:
                    // Declaraciones
                    algoritmo.SJF();
                    break; // break es opcional

                case 4:
                    // Declaraciones
                    algoritmo.SRT();
                    break; // break es opcional

                case 5:
                    // Declaraciones
                    System.out.println("Habías llegado muy lejos...");
                    salir2 = true;
                    break;

                default:
                    System.out.println("No has elegido ninguna de las opciones...");
                    invocarOpciones();
                    invocarMenu();
                    // Declaraciones
            }

        } catch (NumberFormatException e) {
            System.out.println("Debes insertar un número...");
            invocarOpciones();
            invocarMenu();
        }
    }
}
