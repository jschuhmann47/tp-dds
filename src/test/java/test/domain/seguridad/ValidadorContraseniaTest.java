package test.domain.seguridad;

import domain.seguridad.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class ValidadorContraseniaTest {
    ValidadorContrasenia contraseniaTest = new ValidadorContrasenia();

    @Test
    @DisplayName("Una contraseña que es valida cumple con todos los estandares")
    public void contraseniaConEstandares() throws FileNotFoundException {
        Assertions.assertTrue(contraseniaTest.esContraseniaValida("holaHola700"));
    }

    @Test
    @DisplayName("Una contraseña que no tiene numeros es invalida")
    public void contraseniaSinNumero() throws FileNotFoundException {
        Assertions.assertFalse(contraseniaTest.esContraseniaValida("HolaHola"));
    }

    @Test
    @DisplayName("Una contraseña que no tiene mayusculas es invalida")
    public void contraseniaSinMayuscula() throws FileNotFoundException {
        Assertions.assertFalse(contraseniaTest.esContraseniaValida("holahola700"));
    }

    @Test
    @DisplayName("Una contraseña que no tiene minusculas es invalida")
    public void contraseniaSinMinuscula() throws FileNotFoundException {
        Assertions.assertFalse(contraseniaTest.esContraseniaValida("HOLAHOLA700"));
    }

    @Test
    @DisplayName("Una contraseña que tiene menos de 8 caracteres es invalida")
    public void contraseniaMenosCantMinimaCaracteres() throws FileNotFoundException {
        Assertions.assertFalse(contraseniaTest.esContraseniaValida("Hola700"));
    }

    @Test
    @DisplayName("Una contraseña que esta en el top 10000 peores contraseñas es invalida")
    public void contraseniaQueEstaEnElTopPeores() throws FileNotFoundException {
        Assertions.assertFalse(contraseniaTest.esContraseniaValida("PolniyPizdec0211")); //es la 4762 de la lista
    }

}
