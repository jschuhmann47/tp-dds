import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ContactoTest {

    Contacto contacto;


    @BeforeEach
    public void init() throws IOException {
        contacto = new Contacto("", "");

    }

    @Test
    @DisplayName("Contacto: se manda un whatsapp")
    public void whatsapp() {

    }

    @Test
    @DisplayName("Contacto: se manda un mail")
    public void mail() {

    }
}
