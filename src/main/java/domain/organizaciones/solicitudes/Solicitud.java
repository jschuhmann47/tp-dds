package domain.organizaciones.solicitudes;

import domain.organizaciones.entidades.Organizacion;
import domain.organizaciones.entidades.Sector;
import domain.organizaciones.entidades.Trabajador;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "solicitud")
public class Solicitud {
    @Id
    @GeneratedValue
    @Getter
    private int id;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sector_id", referencedColumnName = "id")
    private Sector sectorAIngresar;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trabajador_id",referencedColumnName = "id")
    private Trabajador trabajador;
    @Embedded
    private EstadoSolicitud estadoSolicitud;

    public Solicitud(Sector sectorAIngresar, Trabajador trabajador) {
        this.trabajador = trabajador;
        this.sectorAIngresar = sectorAIngresar;
        this.estadoSolicitud = new EstadoSolicitud();
        estadoSolicitud.setFechaUltimaModificacion(LocalDate.now());
        estadoSolicitud.setEstadoSolicitud(PosibleEstadoSolicitud.PENDIENTE);
    }

    public void aceptarSolicitud(){
        estadoSolicitud.setEstadoSolicitud(PosibleEstadoSolicitud.ACEPTADO);
        estadoSolicitud.setFechaUltimaModificacion(LocalDate.now());
        sectorAIngresar.agregarTrabajador(this.trabajador);
    }


    public void rechazarSolicitud() {
        estadoSolicitud.setEstadoSolicitud(PosibleEstadoSolicitud.RECHAZADO);
        estadoSolicitud.setFechaUltimaModificacion(LocalDate.now());
    }


}
