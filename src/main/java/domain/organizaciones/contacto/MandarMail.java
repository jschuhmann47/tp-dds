package domain.organizaciones.contacto;

import domain.organizaciones.contacto.adapters.EnvioNotificacionMailAdapter;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

public class MandarMail implements MedioNotificacion {

    @Setter
    @Getter
    EnvioNotificacionMailAdapter adapter;

    EMedioNotificacion medio = EMedioNotificacion.MAIL;
    public MandarMail(EnvioNotificacionMailAdapter adapter) {
        this.adapter = adapter;
    }

    public MandarMail() {

    }

    public void notificar(String contenido, String nroTelefono, String mail) throws IOException {
        adapter.notificar(contenido,nroTelefono);
    }

    public EMedioNotificacion getMedio(){
        return medio;
    }
}
