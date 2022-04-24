package domain.seguridad;

public class Numero extends Chequeos {

    public boolean caracterEs(char valor) {
        return Character.isDigit(valor);
    }
}
