package domain.organizaciones.contacto;

import java.io.IOException;

public class MandarMail implements AccionNotificar{
    EnvioNotificacionMailAdapter adapter;

    public MandarMail(EnvioNotificacionMailAdapter adapter) {
        this.adapter = adapter;
    }

    public void notificar(String contenido, String nroTelefono, String mail) throws IOException {
        adapter.notificar(contenido,nroTelefono);
    }
}
