package models.entities.organizaciones.contacto;

import models.entities.organizaciones.contacto.adapters.EnvioNotificacionMailAdapter;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

public class MandarMail extends MedioNotificacion {

    @Setter
    @Getter
    EnvioNotificacionMailAdapter adapter;


    public MandarMail(EnvioNotificacionMailAdapter adapter) {
        this.adapter = adapter;
        this.medio = EMedioNotificacion.MAIL;
    }

    public MandarMail() {
        this.medio = EMedioNotificacion.MAIL;
    }

    public void notificar(String contenido, String nroTelefono, String mail) throws IOException {
        adapter.notificar(contenido,nroTelefono);
    }
}
