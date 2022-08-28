package domain.organizaciones.contacto;

import lombok.Getter;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "contacto")
public class Contacto {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "organizacion_id")
    private int organizacionId;

    @Getter
    @Column(name = "telefono")
    private String nroTelefono;

    @Getter
    @Column(name = "email")
    private String email;

    @Transient //TODO
    public List<AccionNotificar> accionesNotificar;

    public Contacto() {
    }

    public Contacto(String nroTelefono, String mail, AccionNotificar ... acciones) {
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
