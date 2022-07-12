package domain.organizaciones.contacto;

import java.io.IOException;

public class MandarWhatsapp implements AccionNotificar{
    EnvioNotificacionWhatsappAdapter adapter;

    public MandarWhatsapp(EnvioNotificacionWhatsappAdapter adapter) {
        this.adapter = adapter;
    }

    public void notificar(String contenido, String nroTelefono, String mail) throws IOException {
        adapter.notificar(contenido,nroTelefono);
    }
}
