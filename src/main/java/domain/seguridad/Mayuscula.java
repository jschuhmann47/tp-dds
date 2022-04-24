package domain.seguridad;

public class Mayuscula extends Chequeos {

    protected static boolean caracterEs(char valor) {
        return Character.isUpperCase(valor);
    }
}
