package domain.organizaciones;

import domain.CargaDeActividades.entidades.Periodo;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sector")
public class Sector {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "organizacion_id",referencedColumnName = "id")
    public Organizacion organizacion;

    @Column(name = "nombre_sector",nullable = false)
    public String nombreSector;

    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name = "trabajador_sector",joinColumns = @JoinColumn(name = "sector_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "trabajador_id",referencedColumnName = "id"))
    public List<Trabajador> trabajadores;

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
}

