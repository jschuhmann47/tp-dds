package domain.seguridad;

public class Mayuscula extends Chequeo {

    public boolean caracterEs(char valor) {
        return Character.isUpperCase(valor);
    }
}
