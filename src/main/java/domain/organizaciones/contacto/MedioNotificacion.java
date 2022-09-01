package domain.organizaciones.contacto;

import java.io.IOException;

public interface MedioNotificacion {

    void notificar(String contenido,String nroTelefono,String mail) throws IOException;
}
