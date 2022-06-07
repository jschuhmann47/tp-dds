package domain.organizaciones;

import domain.CargaDeDatosAdapter.adapters.CargaDeDatosAdapter;
import domain.geoDDS.Direccion;

import java.io.IOException;
import java.util.List;

public class Organizacion {
    private String clasificacionOrg;
    private List<Trabajador> miembros;
    private String razonSocial;
    private List<Sector> sectores;
    private TipoOrganizacion tipoOrganizacion;
    private Direccion ubicacion;
    private CargaDeDatosAdapter adapter;


    public CargaDeDatosAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(CargaDeDatosAdapter adapter) {
        this.adapter = adapter;
    }



    public void cargarDatos(String archivo) throws IOException {
        adapter.leerArchivoDA(archivo);
    }

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
}