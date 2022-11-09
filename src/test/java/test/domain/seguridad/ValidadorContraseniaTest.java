package test.domain.seguridad;

import models.entities.seguridad.ValidadorContrasenia;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ValidadorContraseniaTest {

    @BeforeAll
    public static void init() throws IOException {
        ValidadorContrasenia.inicializarChequeos();
        ValidadorContrasenia.setearPeoresContrasenias("src/main/java/models/entities/" +
                "seguridad/chequeos/peoresContrasenias.txt");
    }

    @Test
    @DisplayName("Una contraseña que es valida cumple con todos los estandares")
    public void contraseniaConEstandares() {
        Assertions.assertTrue(ValidadorContrasenia.esContraseniaValida("holaHola700"));
    }

    @Test
    @DisplayName("Una contraseña que no tiene numeros es invalida")
    public void contraseniaSinNumero() {
        Assertions.assertFalse(ValidadorContrasenia.esContraseniaValida("HolaHola"));
    }

    @Test
    @DisplayName("Una contraseña que no tiene mayusculas es invalida")
    public void contraseniaSinMayuscula() {
        Assertions.assertFalse(ValidadorContrasenia.esContraseniaValida("holahola700"));
    }

    @Test
    @DisplayName("Una contraseña que no tiene minusculas es invalida")
    public void contraseniaSinMinuscula() {
        Assertions.assertFalse(ValidadorContrasenia.esContraseniaValida("HOLAHOLA700"));
    }

    @Test
    @DisplayName("Una contraseña que tiene menos de 8 caracteres es invalida")
    public void contraseniaMenosCantMinimaCaracteres() {
        Assertions.assertFalse(ValidadorContrasenia.esContraseniaValida("Hola700"));
    }

    @Test
    @DisplayName("Una contraseña que esta en el top 10000 peores contraseñas es invalida")
    public void contraseniaQueEstaEnElTopPeores() {
        Assertions.assertFalse(ValidadorContrasenia.esContraseniaValida("PolniyPizdec0211")); //es la 4762 de la lista
    }

}
