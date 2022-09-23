package models.entities.seguridad.chequeos;

public class Minuscula extends Caracteres {
    public static Minuscula instancia=null;

    public boolean caracterEs(char valor) {
        return Character.isLowerCase(valor);
    }

    public static Minuscula getInstance(){
        if (instancia==null){
            instancia=new Minuscula();
        }
        return instancia;
    }
}
