package domain.seguridad;

public class Mayuscula extends Chequeos {

    public boolean caracterEs(char valor) {
        return Character.isUpperCase(valor);
    }
}
