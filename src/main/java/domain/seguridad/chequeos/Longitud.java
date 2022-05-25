package domain.seguridad.chequeos;

import domain.seguridad.ValidadorContrasenia;

public class Longitud extends Chequeo{

    public boolean chequear(String contrasenia) {
        return ValidadorContrasenia.getCaracteresMinimos() <= contrasenia.length();
    }
}
