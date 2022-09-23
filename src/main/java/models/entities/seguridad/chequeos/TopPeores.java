package models.entities.seguridad.chequeos;

import models.entities.seguridad.ValidadorContrasenia;

import java.util.Objects;

public class TopPeores extends Chequeo{
    public boolean chequear(String contrasenia) {

        for (String leido : ValidadorContrasenia.getTopPeoresContrasenias()){
            if (Objects.equals(leido,contrasenia)){
                return false;
            }
        }
        return true;
    }
}
