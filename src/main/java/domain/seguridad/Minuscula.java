package domain.seguridad;

public class Minuscula extends Chequeo {

    public boolean caracterEs(char valor) {
        return Character.isLowerCase(valor);
    }
}
