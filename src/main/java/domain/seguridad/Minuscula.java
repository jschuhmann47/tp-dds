package domain.seguridad;

public class Minuscula extends Chequeos {

    protected boolean caracterEs(char valor) {
        return Character.isLowerCase(valor);
    }
}
