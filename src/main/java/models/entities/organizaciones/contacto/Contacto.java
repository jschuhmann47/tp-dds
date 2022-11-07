package models.entities.organizaciones.contacto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "contacto")
public class Contacto {
    @Id
    @GeneratedValue
    private int id;

    @Getter
    @Setter
    @Column(name = "nombre")
    private String nombre;

    @Getter
    @Setter
    @Column(name = "apellido")
    private String apellido;

    @Getter
    @Setter
    @Column(name = "telefono",nullable = false)
    private String nroTelefono;

    @Getter
    @Setter
    @Column(name = "email",nullable = false)
    private String email;

    @Transient //se persiste con el enum
    public List<MedioNotificacion> accionesNotificar;

    @ElementCollection
    @CollectionTable(name = "medios_notificacion",joinColumns = @JoinColumn(name = "contacto_id",referencedColumnName = "id"))
    @Enumerated(value = EnumType.STRING)
    private List<EMedioNotificacion> mediosDeNotificacion;

    public Contacto() {
        accionesNotificar = new ArrayList<>();
        this.mediosDeNotificacion = new ArrayList<>();
    }

    public Contacto(String nroTelefono, String mail, MedioNotificacion... acciones) {
        this.nroTelefono = nroTelefono;
        this.email = mail;
        this.setListaDeMedios(Arrays.asList(acciones));

    }

    public Contacto(String nroTelefono, String mail, List<MedioNotificacion> medios) {
        this.nroTelefono = nroTelefono;
        this.email = mail;
        this.setListaDeMedios(medios);

    }

    public void setListaDeMedios(List<MedioNotificacion> medios){
        accionesNotificar = new ArrayList<>();
        accionesNotificar.addAll(medios);
        this.mediosDeNotificacion = new ArrayList<>();
        this.mediosDeNotificacion.addAll(this.accionesNotificar.stream().map(MedioNotificacion::getMedio).collect(Collectors.toList()));
    }

    public void notificar(String contenido){ //todo factory con el enum
        accionesNotificar.forEach(t-> {
            try {
                t.notificar(contenido,this.getNroTelefono(),this.getEmail());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
