package models.entities.organizaciones.contacto;

import lombok.Getter;

import java.io.IOException;

public abstract class MedioNotificacion {

    @Getter
    EMedioNotificacion medio;
    abstract void notificar(String contenido,String nroTelefono,String mail) throws IOException;
}
