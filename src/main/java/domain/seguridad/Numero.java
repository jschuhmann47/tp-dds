package domain.seguridad;

public class Numero extends Chequeos {

    protected boolean caracterEs(char valor) {
        return Character.isDigit(valor);
    }
}
