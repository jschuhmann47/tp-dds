package models.entities.organizaciones.entidades;

import lombok.Getter;
import lombok.Setter;
import models.entities.CargaDeActividades.entidades.Periodo;
import models.entities.organizaciones.solicitudes.Solicitud;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sector")
public class Sector {
    @Id
    @GeneratedValue
    @Getter
    private int id;


    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "organizacion_id",referencedColumnName = "id")
    public Organizacion organizacion;

    @Setter
    @Getter
    @Column(name = "nombre_sector",nullable = false)
    public String nombreSector;

    @Getter
    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name = "trabajador_sector",joinColumns = @JoinColumn(name = "sector_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "trabajador_id",referencedColumnName = "id"))
    public List<Trabajador> trabajadores = new ArrayList<>();

    @Getter
    @OneToMany(mappedBy = "sectorAIngresar",fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public List<Solicitud> solicitudes = new ArrayList<>();

    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    public Sector() {
    }

    public void agregarTrabajador(Trabajador trabajador){
        trabajadores.add(trabajador);
    }

    public void solicitudDeVinculacion(Solicitud solicitudNueva){
        this.getSolicitudes().add(solicitudNueva);
    }

    public void aceptarSolicitud(Solicitud solicitud){
        solicitud.aceptarSolicitud();
    }
    public void rechazarSolicitud(Solicitud solicitud){
        solicitud.rechazarSolicitud();
    }

    public Sector(Organizacion organizacion,String nombreSector,List<Trabajador> trabajadores){
        this.organizacion= organizacion;
        this.nombreSector= nombreSector;
        this.trabajadores= trabajadores;
        organizacion.agregarNuevoSector(this);
    }

    public Double calcularHCSector(Periodo periodo) {
        return this.trabajadores.stream().mapToDouble(t-> {
            try {
                return t.calcularHC(periodo);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).sum();
    }

    public Double calcularHCTotalSector() {
        return this.trabajadores.stream().mapToDouble(Trabajador::calcularHCTotal).sum();
    }
}

