package models.entities.seguridad.chequeos;

public class Minuscula extends Caracteres {
    public boolean caracterEs(char valor) {
        return Character.isLowerCase(valor);
    }

}
