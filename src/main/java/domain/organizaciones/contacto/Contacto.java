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

    @Transient //se persiste con el enum
    public List<MedioNotificacion> accionesNotificar;

    @OneToMany
    @Enumerated(value = EnumType.STRING)
    @JoinColumn(name = "contacto_id",referencedColumnName = "id")
    private List<EMedioNotificacion> mediosDeNotificacion;

    public Contacto() {
    }

    public Contacto(String nroTelefono, String mail, MedioNotificacion... acciones) {
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
