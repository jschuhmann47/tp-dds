package domain.organizaciones;

import java.util.List;

public class Sector {
    public Organizacion organizacion;
    public String nombreSector;
    public List<Trabajador> trabajadores;

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

