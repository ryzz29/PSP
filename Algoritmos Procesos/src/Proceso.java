public class Proceso {

    //Estados
    char nombreProceso;
    int tiempoDeLLegada;
    int rafaga;
    int rafagaInicial;
    int cicloFinal;

    //Constructor
    public Proceso(char pro, int tiemp, int raf){
    this.nombreProceso = pro;
    this.tiempoDeLLegada = tiemp;
    this.rafagaInicial = raf;
    this.rafaga = raf;
    }
}
