package domain.organizaciones;

import domain.geoDDS.Direccion;
import java.util.List;

public class Organizacion {
    private String clasificacionOrg;
    private List<Trabajador> miembros;
    private String razonSocial;
    private List<Sector> sectores;
    private TipoOrganizacion tipoOrganizacion;
    private Direccion ubicacion;


public void agregarNuevoSector(Sector sector){
    sectores.add(sector);
}

public void solicitudDeVinculacion(Trabajador trabajador, Sector sector){
    miembros.add(trabajador);
    trabajador.aceptarSolicitud(sector);
    sector.agregarTrabajador(trabajador);
}

public Organizacion(String clasificacionOrg, List<Trabajador> miembros, String razonSocial, List<Sector> sectores, TipoOrganizacion tipoOrganizacion, Direccion ubicacion){
    this.clasificacionOrg = clasificacionOrg;
    this.miembros = miembros;
    this.razonSocial = razonSocial;
    this.sectores = sectores;
    this.tipoOrganizacion= tipoOrganizacion;
    this.ubicacion = ubicacion;
}



    public void cargarDatos(){
        //TODO lo del excel
    }
}