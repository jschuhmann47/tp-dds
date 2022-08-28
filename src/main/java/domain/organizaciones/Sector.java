package domain.organizaciones;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sector")
public class Sector {
    @Id
    @GeneratedValue
    private int id; //no se si el id se lo pone la org
    @ManyToOne
    @JoinColumn(name = "organizacion_id",referencedColumnName = "id")
    public Organizacion organizacion;

    @Column(name = "nombre_sector")
    public String nombreSector;

    @OneToMany
    @JoinColumn(name = "sector_id",referencedColumnName = "id")
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

}

