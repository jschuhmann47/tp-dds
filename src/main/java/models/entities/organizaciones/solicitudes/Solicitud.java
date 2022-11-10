package models.entities.organizaciones.solicitudes;

import models.entities.organizaciones.entidades.Sector;
import models.entities.organizaciones.entidades.Trabajador;
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

    @Getter
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "sector_id", referencedColumnName = "id")
    private Sector sectorAIngresar;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "trabajador_id",referencedColumnName = "id")
    private Trabajador trabajador;

    @Embedded
    @Getter
    private EstadoSolicitud estadoSolicitud;

    public Solicitud(){

    }

    public Solicitud(Sector sectorAIngresar, Trabajador trabajador) {
        this.trabajador = trabajador;
        this.sectorAIngresar = sectorAIngresar;
        this.estadoSolicitud = new EstadoSolicitud();
        estadoSolicitud.setFechaUltimaModificacion(LocalDate.now());
        estadoSolicitud.setPosibleEstadoSolicitud(PosibleEstadoSolicitud.PENDIENTE);
    }

    public void aceptarSolicitud(){
        estadoSolicitud.setPosibleEstadoSolicitud(PosibleEstadoSolicitud.ACEPTADO);
        estadoSolicitud.setFechaUltimaModificacion(LocalDate.now());
        sectorAIngresar.agregarTrabajador(this.trabajador);
        this.trabajador.agregarSector(this.getSectorAIngresar());
    }


    public void rechazarSolicitud() {
        estadoSolicitud.setPosibleEstadoSolicitud(PosibleEstadoSolicitud.RECHAZADO);
        estadoSolicitud.setFechaUltimaModificacion(LocalDate.now());
    }


}
