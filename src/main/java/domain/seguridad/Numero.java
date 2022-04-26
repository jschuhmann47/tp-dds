package domain.seguridad;

public class Numero extends Chequeo {

    public boolean caracterEs(char valor) {
        return Character.isDigit(valor);
    }

}
