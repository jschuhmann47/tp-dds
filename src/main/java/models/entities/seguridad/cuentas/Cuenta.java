package models.entities.seguridad.cuentas;

import models.entities.seguridad.ValidadorContrasenia;

public abstract class Cuenta {
    private String usuario;
    private String contrasenia;

    public boolean validarContrasenia(){
        return ValidadorContrasenia.esContraseniaValida(this.contrasenia);
    }
}
