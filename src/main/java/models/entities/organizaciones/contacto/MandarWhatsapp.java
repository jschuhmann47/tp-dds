package models.entities.organizaciones.contacto;

import models.entities.organizaciones.contacto.adapters.EnvioNotificacionWhatsappAdapter;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

public class MandarWhatsapp implements MedioNotificacion {

    @Setter
    @Getter
    EnvioNotificacionWhatsappAdapter adapter;
    EMedioNotificacion medio = EMedioNotificacion.WHATSAPP;

    public MandarWhatsapp(EnvioNotificacionWhatsappAdapter adapter) {
        this.adapter = adapter;
    }

    public MandarWhatsapp() {
    }

    public void notificar(String contenido, String nroTelefono, String mail) throws IOException {
        adapter.notificar(contenido,nroTelefono);
    }
    public EMedioNotificacion getMedio(){
        return medio;
    }

}
