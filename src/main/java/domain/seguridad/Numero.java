package domain.seguridad;

public class Numero extends Chequeos {

    protected static boolean caracterEs(char valor) {
        return Character.isDigit(valor);
    }
}
