package test.domain.seguridad;

import models.helpers.HashingHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HashingHelperTest {

    @Test
    @DisplayName("Se hashea una contrasenia")
    public void hash(){
        Assertions.assertEquals("b460b1982188f11d175f60ed670027e1afdd16558919fe47023ecd38329e0b7f",
                HashingHelper.hashear("hola123"));
    }
}
