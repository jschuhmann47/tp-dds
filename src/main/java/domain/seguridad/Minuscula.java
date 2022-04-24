package domain.seguridad;

public class Minuscula extends Chequeos {

    protected static boolean caracterEs(char valor) {
        return Character.isLowerCase(valor);
    }
}
