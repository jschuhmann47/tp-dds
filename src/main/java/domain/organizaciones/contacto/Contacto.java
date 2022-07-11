package domain.organizaciones.contacto;

import java.io.IOException;

public class Contacto {


    private String nroTelefono; //ULTRAMSG
    private String email;

    public EnvioNotificacionWhatsappAdapter whatsappAdapter;
    public EnvioNotificacionMailAdapter mailAdapter;
    
    public Contacto(String nroTelefono, String mail, EnvioNotificacionWhatsappAdapter whatsappAdapter, EnvioNotificacionMailAdapter mailAdapter) {
        this.nroTelefono = nroTelefono;
        this.email = mail;
        this.whatsappAdapter = whatsappAdapter;
        this.mailAdapter = mailAdapter;
    }

    public void notificar(String link) throws IOException {
        sendMAIL(link);
        sendWPP(link);
    }

    private void sendWPP(String contenido) throws IOException {
        whatsappAdapter.notificar(contenido,this.nroTelefono);
    }

    private void sendMAIL(String contenido) {
        mailAdapter.notificar(contenido,this.email);
    }


}
