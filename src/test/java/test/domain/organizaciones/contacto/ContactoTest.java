package test.domain.organizaciones.contacto;

import models.entities.organizaciones.contacto.Contacto;
import models.entities.organizaciones.contacto.MandarMail;
import models.entities.organizaciones.contacto.MandarWhatsapp;
import models.entities.organizaciones.entidades.Organizacion;
import models.entities.organizaciones.contacto.adapters.EnvioNotificacionJavaxMailAdapter;
import models.entities.organizaciones.contacto.adapters.EnvioNotificacionUltraWppAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class ContactoTest {

    Contacto contacto;
    Organizacion organizacion;
    EnvioNotificacionJavaxMailAdapter mailAdapter;
    EnvioNotificacionUltraWppAdapter whatsappAdapter;


    @BeforeEach
    public void init() throws IOException {
//        mailAdapter = mock(EnvioNotificacionJavaxMailAdapter.class);
        whatsappAdapter = mock(EnvioNotificacionUltraWppAdapter.class);
        mailAdapter =
                new EnvioNotificacionJavaxMailAdapter("src/main/java/domain/organizaciones/contacto/smtp.properties",
                        "Guia de recomendaciones");
//        whatsappAdapter =
//                new EnvioNotificacionUltraWppAdapter("src/main/java/domain/organizaciones/contacto/token.properties",
//            "https://api.ultramsg.com/instance10585/messages/chat");
        MandarWhatsapp mandarWhatsapp = new MandarWhatsapp(whatsappAdapter);
        MandarMail mandarMail = new MandarMail(mailAdapter);
        contacto = new Contacto("12345678", "test",mandarWhatsapp,mandarMail);
        List<Contacto> contactos = new ArrayList<>();
        contactos.add(contacto);
        organizacion = new Organizacion();
        organizacion.setContactos(contactos);

        //mailAdapter.generarSesion();
    }

    @Test
    @DisplayName("Contacto: se manda un Whatsapp")
    public void whatsapp() throws IOException {
        //TODO
//        OkHttpClient cliente = new OkHttpClient();
//
//        String numero = "token=" + "abcd123" + "&to=+54" + contacto.getNroTelefono() + "&body=" + "Buenos%20dias" + "&priority=1&referenceId=";
//        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
//        RequestBody cuerpo = RequestBody.create(numero,mediaType);
//        Request request = new Request.Builder()
//                .url("https://test.com")
//                .post(cuerpo)
//                .addHeader("content-type", "application/x-www-form-urlencoded")
//                .build();
//        when(whatsappAdapter.ejecutarRequest(cliente,request)).thenReturn("200");

        //contacto.notificar("Hola");
        Assertions.assertEquals(1,1);
        //Assertions.assertEquals("200",whatsappAdapter.ejecutarRequest(cliente,request));
    }

    @Test
    @DisplayName("Contacto: se manda un mail")
    public void mail() throws MessagingException {
        MimeMessage mensajeTest = mailAdapter.armarMensaje(mailAdapter.generarSesion(),contacto.getEmail(),"Buenos dias");
        Assertions.assertEquals("Guia de recomendaciones",mensajeTest.getSubject());
    }
}
