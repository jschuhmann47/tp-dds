package domain.seguridad.chequeos;

public class Numero extends Caracteres {
    private static Numero instancia=null;

    public boolean caracterEs(char valor) {
        return Character.isDigit(valor);
    }

    public static Numero getInstance(){
        if (instancia==null){
            instancia=new Numero();
        }
        return instancia;
    }
}
