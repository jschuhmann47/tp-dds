package domain.organizaciones;

import okhttp3.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class Contacto {

    private String whatsapp; //ULTRAMSG

    private String email;
    
    public Contacto(String wpp, String mail) {
        this.whatsapp = wpp;
        this.email = mail;
    }

    public void notificar(String link) throws IOException {
        sendMAIL(link);
        sendWPP(link);
    }

    private void sendWPP(String link) throws IOException {

        OkHttpClient cliente = new OkHttpClient();

        String numero = "token=oihx1vl2z8p3wnja&to=+54"+this.whatsapp+"&body="+link+"&priority=1&referenceId=";

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody cuerpo = RequestBody.create(mediaType, numero);
        Request request = new Request.Builder()
                .url("https://api.ultramsg.com/instance10585/messages/chat")
                .post(cuerpo)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        Response response = cliente.newCall(request).execute();
    }

    private void sendMAIL(String link) {

        String remitente = "ing.gsorzzoni";

        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", "latortaesunamentira147");    //La clave de la cuenta
        props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
        props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
        props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(remitente));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(this.email));   //Se podrían añadir varios de la misma manera
            message.setSubject("Guia de recomendaciones");
            message.setText(link);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, "latortaesunamentira147");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }


}
