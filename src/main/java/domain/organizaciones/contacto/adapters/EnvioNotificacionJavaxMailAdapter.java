package domain.organizaciones.contacto.adapters;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Properties;

public class EnvioNotificacionJavaxMailAdapter implements EnvioNotificacionMailAdapter {
    private HashMap<String,String> informacionDeSmtp = new HashMap<>();
    private String tituloMail;

    public EnvioNotificacionJavaxMailAdapter(String propertiesPath, String tituloMail){
        this.tituloMail = tituloMail;
        Properties FEconfigs = new Properties();
        try{
            InputStream input = Files.newInputStream(new File(propertiesPath).toPath());
            FEconfigs.load(input);
        } catch(IOException e){
            e.printStackTrace();
        }
        for (String key : FEconfigs.stringPropertyNames()){
            String value = FEconfigs.getProperty(key);
            informacionDeSmtp.put(key, value);
        }
    }

    public void notificar(String contenido, String direccionCorreo) {
        Session session = this.generarSesion();
        try {
            MimeMessage message = this.armarMensaje(session, direccionCorreo, contenido);
            this.enviarMensaje(session,message);
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }

    }

    public MimeMessage armarMensaje(Session session, String direccionCorreo,String contenido) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(informacionDeSmtp.get("SMTP_HOST")));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(direccionCorreo));   //Se podrían añadir varios de la misma manera
        message.setSubject(this.tituloMail);
        message.setText(contenido);
        return message;
    }

    private void setearConfiguracion(Properties props){
        props.put("mail.smtp.host", informacionDeSmtp.get("SMTP_HOST"));  //El servidor SMTP de Google
        props.put("mail.smtp.user", informacionDeSmtp.get("SMTP_USER"));
        props.put("mail.smtp.clave", informacionDeSmtp.get("SMTP_PASSWORD"));    //La clave de la cuenta
        props.put("mail.smtp.auth", informacionDeSmtp.get("SMTP_AUTH"));    //Usar autenticación mediante usuario y clave
        props.put("mail.smtp.starttls.enable", informacionDeSmtp.get("SMTP_STARTTLS_ENABLE")); //Para conectar de manera segura al servidor SMTP
        props.put("mail.smtp.port", informacionDeSmtp.get("SMTP_PORT")); //El puerto SMTP seguro de Google
    }

    public void enviarMensaje(Session session,MimeMessage message) throws MessagingException {
        Transport transport = session.getTransport("smtp");
        transport.connect("smtp.gmail.com", informacionDeSmtp.get("SMTP_HOST"), informacionDeSmtp.get("SMTP_PASSWORD"));
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    public Session generarSesion(){
        Properties props = System.getProperties();
        this.setearConfiguracion(props);
        return Session.getDefaultInstance(props);
    }
}
