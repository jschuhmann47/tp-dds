package domain.seguridad;

public class Mayuscula extends Chequeos {

    protected boolean caracterEs(char valor) {
        return Character.isUpperCase(valor);
    }
}
