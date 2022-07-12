package domain.organizaciones.contacto;

import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Contacto {

    @Getter
    private String nroTelefono;
    @Getter
    private String email;

    public List<AccionNotificar> accionesNotificar;
    
    public Contacto(String nroTelefono, String mail,AccionNotificar ... acciones) {
        this.nroTelefono = nroTelefono;
        this.email = mail;
        accionesNotificar = new ArrayList<>();
        accionesNotificar.addAll(Arrays.asList(acciones));
    }

    public void notificar(String contenido){
        accionesNotificar.forEach(t-> {
            try {
                t.notificar(contenido,this.getNroTelefono(),this.getEmail());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
