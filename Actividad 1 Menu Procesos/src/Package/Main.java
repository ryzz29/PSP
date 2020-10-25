package Package;

public class Main {
    public static void main(String Args[]){
        if (System.getProperty("os.name").toLowerCase().contains("windows")){
            Windows.ejecutarProcesos();
        } else { Linux.ejecutarProcesos();}
    }
}