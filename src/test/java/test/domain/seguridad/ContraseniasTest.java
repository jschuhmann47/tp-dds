package test.domain.seguridad;

import domain.seguridad.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class ContraseniasTest {
    Contrasenias contraseniaTest = new Contrasenias();

    @Test
    @DisplayName("Una contraseña que es valida cumple con todos los estandares")
    public void contraseniaConEstandares() throws FileNotFoundException {
        contraseniaTest.setContrasenia("holaHola700");
        Assertions.assertTrue(contraseniaTest.esContraseniaValida(contraseniaTest.getContrasenia()));
    }

    @Test
    @DisplayName("Una contraseña que no tiene numeros es invalida")
    public void contraseniaSinNumero() throws FileNotFoundException {
        contraseniaTest.setContrasenia("HolaHola");
        Assertions.assertFalse(contraseniaTest.esContraseniaValida(contraseniaTest.getContrasenia()));
    }

    @Test
    @DisplayName("Una contraseña que no tiene mayusculas es invalida")
    public void contraseniaSinMayuscula() throws FileNotFoundException {
        contraseniaTest.setContrasenia("holahola700");
        Assertions.assertFalse(contraseniaTest.esContraseniaValida(contraseniaTest.getContrasenia()));
    }

    @Test
    @DisplayName("Una contraseña que no tiene minusculas es invalida")
    public void contraseniaSinMinuscula() throws FileNotFoundException {
        contraseniaTest.setContrasenia("HOLAHOLA700");
        Assertions.assertFalse(contraseniaTest.esContraseniaValida(contraseniaTest.getContrasenia()));
    }

    @Test
    @DisplayName("Una contraseña que tiene menos de 8 caracteres es invalida")
    public void contraseniaMenosCantMinimaCaracteres() throws FileNotFoundException {
        contraseniaTest.setContrasenia("Hola700");
        Assertions.assertFalse(contraseniaTest.esContraseniaValida(contraseniaTest.getContrasenia()));
    }

    @Test
    @DisplayName("Una contraseña que esta en el top 10000 peores contraseñas es invalida")
    public void contraseniaQueEstaEnElTopPeores() throws FileNotFoundException {
        contraseniaTest.setContrasenia("PolniyPizdec0211"); //es la 4762 de la lista
        Assertions.assertFalse(contraseniaTest.esContraseniaValida(contraseniaTest.getContrasenia()));
    }

}
