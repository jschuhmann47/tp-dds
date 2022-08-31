package domain.organizaciones.contacto.adapters;

import java.io.IOException;

public interface EnvioNotificacionWhatsappAdapter {

    void notificar(String contenido,String nroTelefono) throws IOException;
}
