package domain.seguridad;

public class Minuscula extends Chequeos {

    public boolean caracterEs(char valor) {
        return Character.isLowerCase(valor);
    }
}
