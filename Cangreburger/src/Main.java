public class Main {
    static final int N_Clientes = 5;
    static final int N_Cocineros = 5;

    public static void main(String[] args) {

        //Bandeja en la cual confluirán clientes y cocineros
        Bandeja bandeja = new Bandeja();

        //Lista de clientes y cocineros
        Cliente[] clientes = new Cliente[N_Clientes];
        Cocinero[] cocineros = new Cocinero[N_Cocineros];

        //Generamos a los cocineros según el número de cocineros establecido
        for(int i = 0 ; i < N_Cocineros ; i++){
            cocineros[i] = new Cocinero(i, bandeja);
        }

        //Generamos a los cocineros según el número de cocineros establecido
        for(int i = 0 ; i < N_Clientes ; i++){
            clientes[i] = new Cliente(i, bandeja);
        }

        //Ponemos a los cocineros a trabajar en la cocina
        for(int i = 0 ; i < N_Cocineros ; i++){
            cocineros[i].start();
        }

        //Ponemos a los clientes a empezar a buscar las hamburguesas
        for(int i = 0 ; i < N_Clientes ; i++){
            clientes[i].start();
        }

    }
}
