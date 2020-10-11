package Package;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Windows {
    private static String opcion;
    private static Scanner sn = new Scanner(System.in);
    private static ProcessBuilder processBuilder = new ProcessBuilder();
    private static boolean salir = false;

    /**
     * Método que comienza la salida por consola, con su boolean para cerrar el ciclo
     */
    public static void ejecutarProcesos(){ //Usamos el boolean salir para que cese en su repetición del "menú"
        while (!salir) {
            invocarOpciones(); //Imprimimos por pantalla las opciones que tiene nuestro usuario
            invocarMenu();     //Comenzamos ya con el menú en cuanto a programación
        }
    }

    /**
     * Método que comienza la salida por consola de las opciones del usuario
     */
    public static void invocarOpciones(){ //Imprimimos por pantalla las opciones que tiene nuestro usuario
        System.out.println("1. Crear una carpeta dada una ruta y el nombre");
        System.out.println("2. Crear un fichero dada la ruta y el nombre");
        System.out.println("3. Listar todas las interfaces de red de nuestro ordenador");
        System.out.println("4. Mostrar la IP del ordenador dado el nombre de la interfaz de red");
        System.out.println("5. Mostrar la dirección MAC dado el nombre de la interfaz de red");
        System.out.println("6. Comprobar conectividad con internet");
        System.out.println("7. Salir");
        System.out.println("0. Repetir las opciones disponibles");
    }

    /**
     * Método que comienza el input del usuario y lo utiliza para cargar el método que el usuario desea utilizar
     */
    public static void invocarMenu(){ //Comenzamos ya con el menú en cuanto a programación
            try {                     //Pedimos un input del usuario
                System.out.println("Escribe el número de una de las opciones anteriores");
                opcion = sn.next();

                while (compruebaNumeralidad(opcion)) { //Comprobamos que sea un número para impedir que nos claven una letra...
                    System.out.println("Venga hombre, que no es tan dificil, del 1 al 7... (0 para repetir)");
                    opcion = sn.next();
                }

                //Pasamos el string a int dado que estamos seguros de que es un número porque comprobamos su numeralidad
                int i = Integer.parseInt(opcion);

                //Un switch sencillo, normal y corriente con sus opciones correspondientes a las de invocarOpciones()
                switch (i) {
                    case 0:
                        break; // break es opcional

                    case 1:
                        crearCarpeta();
                        break; // break es opcional

                    case 2:
                        crearFichero();
                        break; // break es opcional

                    case 3:
                        // Declaraciones
                        listarInterfacesRed();
                        break; // break es opcional

                    case 4:
                        // Declaraciones
                        mostrarIpDadaInterfaz();
                        break; // break es opcional

                    case 5:
                        // Declaraciones
                        mostrarMacDadaInterfaz();
                        break; // break es opcional

                    case 6:
                        // Declaraciones
                        comprobarConectividad();
                        break; // break es opcional

                    case 7:
                        // Declaraciones
                        System.out.println("¡Hasta luego!");
                        salir = true;  // Este boolean nos permite cerrar la ejecución
                        break; // break es opcional

                }
            } catch (IOException e) {
                System.out.println("IOException | InterruptedException");
            }
    }

    /**
     * Comprueba que el input del usuario (s) sea un número
     * evitando que el usuario pueda poner un string y además que se salga de las opciones deseadas.
     * @param s
     * @return
     */
    private static boolean compruebaNumeralidad(String s){ //!.equals aporta falso si es un número "bueno", podría haberse hecho al revés
        boolean flip = (!s.equals("0") & !s.equals("1") & !s.equals("2") & !s.equals("3") & !s.equals("4") & !s.equals("5") & !s.equals("6") & !s.equals("7"));
        return flip; //flip porque me gustó la palabra, sin más
    }

    /**
     * Comprueba que el input del usuario sea S o N
     * @param s
     * @return
     */
    private static boolean compruebaSN(String s){ //!.equals aporta falso si es un número, podría haberse hecho al revés
        boolean flip = (!s.toLowerCase().equals("s") & !s.toLowerCase().equals("n"));
        return flip; //flip porque me gustó la palabra, sin más
    }

    /**
     * Crea una carpeta basado en el input del usuario que nos introducirá una ruta y un nombre de carpeta
     * Tendremos en cuenta si la ruta existe y si la carpeta existe también.
     * @throws IOException
     */
    private static void crearCarpeta() throws IOException { //1. Crear una carpeta dada una ruta y el nombre
        //Pedimos un input para la ruta y otro para la carpeta
        System.out.println("Introduce la ruta:");
        String ruta = sn.next();

        System.out.println("Introduce el nombre de la carpeta a crear:");
        String carpeta = sn.next();

        //Creamos una ruta que contenga la ruta, la carpeta y el comando que vamos a usar, una línea muy útil
        String rutaTotal = ruta + "\\" + carpeta;
        String comando = "mkdir " + ruta + "\\" + carpeta;

        if (!checkRuta(ruta)) { //Comprobamos que la ruta exista
            System.out.println("La ruta no existe");
        } else {
            if (!checkRuta(rutaTotal)) {
                processBuilder.command("cmd.exe", "/c", comando);
                processBuilder.start();
                System.out.println("Carpeta creada con éxito");
            } else {
                System.out.println("Esa carpeta ya existe...");
            }
        }
    }

    /**
     * Comprueba si la rutata dada existe
     * @param ruta
     * @return
     */
    private static boolean checkRuta(String ruta) { //
        return new File(ruta).exists();
    }

    /**
     * Crea una carpeta basado en el input del usuario que nos introducirá una ruta y un nombre de carpeta
     * Tendremos en cuenta si la ruta existe y si el archivo existe también.
     * @throws IOException
     */
    private static void crearFichero() throws IOException { //2. Crear un fichero dada la ruta y el nombre
        System.out.println("Introduce la ruta:");
        String ruta = sn.next();

        System.out.println("Introduce el nombre del archivo a crear:");
        String archivo = sn.next();

        String rutaTotal = "type NUL > " + ruta + "\\" + archivo;
        if (!checkRuta(ruta)) {
            System.out.println("La ruta no existe");
        } else {
            if (checkRuta(ruta+"\\"+archivo)) {
                processBuilder.command("cmd.exe", "/c", rutaTotal);
                processBuilder.start();
                System.out.println("Archivo cread con éxito");
            } else {
                System.out.println("El archivo para la ruta dada ya existe...");
            }
        }
    }

    /**
     * Lista todas las interfaces de red de nuestro ordenador
     * @throws IOException
     * @throws InterruptedException
     */
    private static void listarInterfacesRed() throws IOException{ //3. Listar todas las interfaces de red de nuestro ordenador
        processBuilder.command("cmd.exe", "/c", "ipconfig");
        processBuilder.start();

        try {
            Process process = processBuilder.start();
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(),"Cp850"));

            //Guardamos en un buffer la salida del proceso
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (process.waitFor() == 0) {
                System.out.println(buffer);
            } else {
                System.out.println("Se lio chiquita...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * Muestra la IP del ordenador dado el nombre de la interfaz de red
     * @throws IOException
     */
    private static void mostrarIpDadaInterfaz() throws IOException { //4. Mostrar la IP del ordenador dado el nombre de la interfaz de red
        //Utilizamos el boolean mal para saber si la búsqueda ha salido mal
        boolean mal = true;
        String adaptador = "";

        //Preguntamos si es un adaptador de ethernet y en caso afirmativo añadimos al adaptador "Adaptador de Ethernet"
        System.out.println("¿Es un adaptador de ethernet? S/N");
        String answer1 = sn.next();

        while (compruebaSN(answer1)) { //Comprobamos que sea "s o n", nos vale mayúsculas y minúsculas
            System.out.println("Venga hombre, que no es tan dificil, introduce S para sí o N para no...");
            answer1 = sn.next();
        }

        if (answer1.toLowerCase().equals("s")) {
            System.out.println("Perfecto, dime el nombre del adaptador");
            adaptador = "Adaptador de Ethernet " + sn.next();

        } else if (answer1.toLowerCase().equals("n")) {
            System.out.println("¿Es un adaptador inalambrico? S/N");
            String answer2 = sn.next();

            //Preguntamos si es un adaptador de red inalambrico y en caso afirmativo añadimos al adaptador "Adaptador de Lan inalámbrica"
            while (compruebaSN(answer2)) { //Comprobamos que sea "s o n", nos vale mayúsculas y minúsculas
                System.out.println("Venga hombre, que no es tan dificil, introduce S para sí o N para no...");
                answer2 = sn.next();

            } if (answer2.toLowerCase().equals("s")) {
                System.out.println("Perfecto, dime el nombre del adaptador");
                adaptador = "Adaptador de Lan inalámbrica " + sn.next();

            } else if (answer2.toLowerCase().equals("n")) {
                System.out.println("Bueno, probemos a ver que sale... Dime el nombre del adaptador");
                adaptador = sn.next();
            }
        }

            try { //Lanzamos el proceso
            processBuilder.command("cmd.exe", "/c", "ipconfig");
            processBuilder.start();
            Process process = processBuilder.start();
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(),"Cp850"));

            //Guardamos en un buffer la salida del proceso
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (process.waitFor() == 0) {

                //Leemos la salida del comando utilizado
                Scanner scan = new Scanner(buffer.toString());

                //Utilizando un bucle while, buscamos la línea que contenga el nombre del adaptador dado
                while (scan.hasNextLine()){
                    String oneLine = scan.nextLine();
                    if (oneLine.toLowerCase().contains(adaptador.toLowerCase())){ //Cuando llegamos a la línea que contiene dicho nombre
                        while (scan.hasNextLine()) {
                            //Utilizando un bucle while, buscamos la línea que contenga "IPv4" para devolver la ip
                            oneLine = scan.nextLine();
                            if (oneLine.contains("IPv4")){
                                //Le damos un poco de formato al texto a imprimir y done
                                int z = oneLine.indexOf(":");
                                System.out.println("La ip de este ordenador es: " + oneLine.substring(z+2));
                                //Cambiamos el boolean porque la búsqueda ha sido un éxito (o eso pensamos)
                                mal = false;
                                break;
                            }
                        }
                    }
                }

                if (mal){ //Esto lo imprimiríamos si no encontráramos una línea de dirección física...
                    System.out.println("Creo que te has equivocado de adaptador...");
                    mostrarIpDadaInterfaz();
                }
            } else {
                System.out.println("Se lio chiquita...");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * Muestra la mac del ordenador dado el nombre de la interfaz de red
     * @throws IOException
     */
    private static void mostrarMacDadaInterfaz() throws IOException  { //5. Mostrar la dirección MAC dado el nombre de la interfaz de red
        //Utilizamos el boolean mal para saber si la búsqueda ha salido mal
        boolean mal = true;
        String adaptador = "";

        //Preguntamos si es un adaptador de ethernet y en caso afirmativo añadimos al adaptador "Adaptador de Ethernet"
        System.out.println("¿Es un adaptador de ethernet? S/N");
        String answer1 = sn.next();

        while (compruebaSN(answer1)) { //Comprobamos que sea "s o n", nos vale mayúsculas y minúsculas
            System.out.println("Venga hombre, que no es tan dificil, introduce S para sí o N para no...");
            answer1 = sn.next();
        }

        if (answer1.toLowerCase().equals("s")) {
            System.out.println("Perfecto, dime el nombre del adaptador");
            adaptador = "Adaptador de Ethernet " + sn.next();

        } else if (answer1.toLowerCase().equals("n")) {
            System.out.println("¿Es un adaptador inalambrico? S/N");
            String answer2 = sn.next();

            //Preguntamos si es un adaptador de red inalambrico y en caso afirmativo añadimos al adaptador "Adaptador de Lan inalámbrica"
            while (compruebaSN(answer2)) { //Comprobamos que sea "s o n", nos vale mayúsculas y minúsculas
                System.out.println("Venga hombre, que no es tan dificil, introduce S para sí o N para no...");
                answer2 = sn.next();

            } if (answer2.toLowerCase().equals("s")) {
                System.out.println("Perfecto, dime el nombre del adaptador");
                adaptador = "Adaptador de Lan inalámbrica " + sn.next();

            } else if (answer2.toLowerCase().equals("n")) {
                System.out.println("Bueno, probemos a ver que sale... Dime el nombre del adaptador");
                adaptador = sn.next();
            }
        }

        try { //Lanzamos el proceso
            processBuilder.command("cmd.exe", "/c", "ipconfig /all");
            processBuilder.start();
            Process process = processBuilder.start();
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(),"Cp850"));

            //Guardamos en un buffer la salida del proceso
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }


            if (process.waitFor() == 0) {

                //Leemos la salida del comando utilizado
                Scanner scan = new Scanner(buffer.toString());

                //Utilizando un bucle while, buscamos la línea que contenga el nombre del adaptador dado
                while (scan.hasNextLine()){
                    String oneLine = scan.nextLine();
                    if (oneLine.toLowerCase().contains(adaptador.toLowerCase())){ //Cuando llegamos a la línea que contiene dicho nombre
                        while (scan.hasNextLine()) {
                            //Utilizando un bucle while, buscamos la línea que contenga "Dirección física" para devolver la mac
                            oneLine = scan.nextLine();
                            if (oneLine.contains("Dirección física")){
                                //Le damos un poco de formato al texto a imprimir y done
                                int z = oneLine.indexOf(":");
                                System.out.println("La mac de este ordenador es: " + oneLine.substring(z+2));
                                //Cambiamos el boolean porque la búsqueda ha sido un éxito (o eso pensamos)
                                mal = false;
                                break;
                            }
                        }
                    }
                }
                if (mal){ //Esto lo imprimiríamos si no encontráramos una línea de dirección física...
                    System.out.println("Creo que te has equivocado de adaptador...");
                    mostrarMacDadaInterfaz();
                }
            } else {
                System.out.println("Se lio chiquita...");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * Nos imprime por pantalla un mensaje dependiendo de si hay o no conexión a internet
     * @throws IOException
     */
    private static void comprobarConectividad() throws IOException  { //6. Comprobar conectividad con internet
        //Utilizamos el boolean conexion para saber si hay conexión, empezamos suponiendo que no la hay
        boolean conexion = false;

        processBuilder.command("cmd.exe", "/c", "ping www.google.com -n 1");
        processBuilder.start();

        try {
            Process process = processBuilder.start();
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(),"Cp850"));

            //Guardamos en un buffer la salida del proceso
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
                //Leemos la salida del comando utilizado
                Scanner scan = new Scanner(buffer.toString());

                while (scan.hasNextLine()) {
                    //Usamos un bucle while que nos vaya buscando la línea 0% perdidos en caso de que haya internet
                    String oneLine = scan.nextLine();
                    if (oneLine.toLowerCase().contains("0% perdidos")) { //Si encontramos 0% perdidos...
                        System.out.println("Hay conexión a internet.");
                        conexion = true;
                        break;
                    }
                }
                if (!conexion){ //En caso de no haber encontrado 0% perdidos...
                    System.out.println("No hay conexión a internet.\n");
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

