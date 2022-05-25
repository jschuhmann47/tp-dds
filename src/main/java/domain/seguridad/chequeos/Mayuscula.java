package domain.seguridad.chequeos;

public class Mayuscula extends Caracteres {
    public static Mayuscula instancia=null;

    public boolean caracterEs(char valor) {
        return Character.isUpperCase(valor);
    }

    public static Mayuscula getInstance(){
        if (instancia==null){
            instancia=new Mayuscula();
        }
        return instancia;
    }

}
