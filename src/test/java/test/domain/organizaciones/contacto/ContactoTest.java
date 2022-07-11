package test.domain.organizaciones.contacto;

import domain.organizaciones.Organizacion;
import domain.organizaciones.contacto.Contacto;
import domain.organizaciones.contacto.EnvioNotificacionJavaxMailAdapter;
import domain.organizaciones.contacto.EnvioNotificacionUltraWppAdapter;
import domain.organizaciones.contacto.EnvioNotificacionWhatsappAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ContactoTest {

    Contacto contacto;
    Organizacion organizacion;


    @BeforeEach
    public void init() throws IOException {
        EnvioNotificacionJavaxMailAdapter mailAdapter = mock(EnvioNotificacionJavaxMailAdapter.class);
        EnvioNotificacionUltraWppAdapter whatsappAdapter = mock(EnvioNotificacionUltraWppAdapter.class);
//        EnvioNotificacionJavaxMailAdapter mailAdapter =
//                new EnvioNotificacionJavaxMailAdapter("src/main/java/domain/organizaciones/contacto/smtp.properties");
//        EnvioNotificacionUltraWppAdapter whatsappAdapter =
//                new EnvioNotificacionUltraWppAdapter("src/main/java/domain/organizaciones/contacto/token.properties",
//            "https://api.ultramsg.com/instance10585/messages/chat");
        contacto = new Contacto("1124551580", "test",
                whatsappAdapter, mailAdapter);
        List<Contacto> contactos = new ArrayList<>();
        contactos.add(contacto);
        organizacion.setContactos(contactos);
    }

    @Test
    @DisplayName("Contacto: se manda un whatsapp")
    public void whatsapp() throws IOException {

    }

    @Test
    @DisplayName("Contacto: se manda un mail")
    public void mail() {

    }
}
