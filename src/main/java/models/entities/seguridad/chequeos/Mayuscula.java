package models.entities.seguridad.chequeos;

public class Mayuscula extends Caracteres {

    public boolean caracterEs(char valor) {
        return Character.isUpperCase(valor);
    }

}
