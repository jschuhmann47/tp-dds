package models.entities.seguridad.chequeos;

import models.entities.seguridad.ValidadorContrasenia;

public class Longitud extends Chequeo{

    public boolean chequear(String contrasenia) {
        return ValidadorContrasenia.getCaracteresMinimos() <= contrasenia.length();
    }
}
