package domain.organizaciones.contacto;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.Properties;
public class EnviarMailAux {


    private final Properties properties;
    private Session session;

    public EnviarMailAux(String ruta) throws IOException {
        this.properties = new Properties();
        loadConfig(ruta);
    }

    private void loadConfig(String ruta) throws IOException {
        InputStream is = Files.newInputStream(Paths.get(ruta));

        this.properties.load(is);
    }

    private void checkConfig() throws InvalidParameterException {
        String[] keys = {
                "mail.smtp.host",
                "mail.smtp.port",
                "mail.smtp.user",
                "mail.smtp.password",
                "mail.smtp.starttls.enable",
                "mail.smtp.auth"
        };

        for (String key : keys) {
            if (this.properties.get(key) == null) {
                throw new InvalidParameterException("No existe la clave "
                        + key);
            }

        }
    }

    public void enviarEmail(String asunto, String mensaje, String correo) throws MessagingException {

        MimeMessage contenedor = new MimeMessage(session);
        contenedor.setFrom(new InternetAddress((String) this.properties.get("mail.smtp.user")));
        contenedor.addRecipient(Message.RecipientType.TO,new InternetAddress((String) this.properties.get(correo)));
        contenedor.setSubject(asunto);
        contenedor.setText(mensaje);

        Transport t = session.getTransport("smtp");

        t.connect((String) this.properties.get("mail.smtp.user"),(String) this.properties.get("mail.smtp.password"));
        t.sendMessage(contenedor,contenedor.getAllRecipients() );
    }



}
