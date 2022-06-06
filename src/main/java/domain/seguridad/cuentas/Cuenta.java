package domain.seguridad.cuentas;

import domain.seguridad.ValidadorContrasenia;

public abstract class Cuenta {
    private String usuario;
    private String contrasenia;

    public boolean validarContrasenia(){
        return ValidadorContrasenia.esContraseniaValida(this.contrasenia);
    }
}
