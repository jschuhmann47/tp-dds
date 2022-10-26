package models.entities.organizaciones.contacto;

import models.entities.organizaciones.contacto.adapters.EnvioNotificacionWhatsappAdapter;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

public class MandarWhatsapp extends MedioNotificacion {

    @Setter
    @Getter
    EnvioNotificacionWhatsappAdapter adapter;

    public MandarWhatsapp(EnvioNotificacionWhatsappAdapter adapter) {
        this.adapter = adapter;
        this.medio = EMedioNotificacion.WHATSAPP;
    }

    public MandarWhatsapp() {
        this.medio = EMedioNotificacion.WHATSAPP;
    }

    public void notificar(String contenido, String nroTelefono, String mail) throws IOException {
        adapter.notificar(contenido,nroTelefono);
    }

}
