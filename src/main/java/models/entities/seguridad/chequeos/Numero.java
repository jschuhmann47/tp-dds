package models.entities.seguridad.chequeos;

public class Numero extends Caracteres {
    public boolean caracterEs(char valor) {
        return Character.isDigit(valor);
    }

}
