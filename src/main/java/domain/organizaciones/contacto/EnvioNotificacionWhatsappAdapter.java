package domain.organizaciones.contacto;

import java.io.IOException;

public interface EnvioNotificacionWhatsappAdapter {

    void notificar(String contenido,String nroTelefono) throws IOException;
}
