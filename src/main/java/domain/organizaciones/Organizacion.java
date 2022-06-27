package domain.organizaciones;

import domain.CargaDeDatosAdapter.CargaDeDatos;
import domain.CargaDeDatosAdapter.adapters.CargaDeDatosAdapter;
import domain.CargaDeDatosAdapter.entidades.ActividadDA;
import domain.calculoHC.CalculoHC;
import domain.calculoHC.HuellaCarbono;
import domain.geoDDS.Direccion;

import java.io.IOException;
import java.util.List;

public class Organizacion {
    private ClasificacionOrganizacion clasificacionOrg;
    private List<Trabajador> miembros;
    private String razonSocial;
    private List<Sector> sectores;
    private TipoOrganizacion tipoOrganizacion;
    private Direccion ubicacion;
    private CargaDeDatos actividades;
    private List<Contacto> contactos;

    public Organizacion(List<Trabajador> miembros, List<Sector> sectores) {
        this.miembros = miembros;
        this.sectores = sectores;
    }


    public void cargarDatos(String archivo) throws IOException {
        actividades.cargarDatos(archivo);
    }

    public void agregarNuevoSector(Sector sector){
        sectores.add(sector);
    }

    public void solicitudDeVinculacion(Trabajador trabajador, Sector sector){
        miembros.add(trabajador);
        trabajador.solicitudAceptada(sector);
        sector.agregarTrabajador(trabajador);
    }

    public Organizacion(ClasificacionOrganizacion clasificacionOrg, List<Trabajador> miembros,
                        String razonSocial, List<Sector> sectores, TipoOrganizacion tipoOrganizacion,
                        Direccion ubicacion){

        this.clasificacionOrg = clasificacionOrg;
        this.miembros = miembros;
        this.razonSocial = razonSocial;
        this.sectores = sectores;
        this.tipoOrganizacion= tipoOrganizacion;
        this.ubicacion = ubicacion;

    }

    public Double calcularHCEnAnio(Integer anio) throws Exception {
        return CalculoHC.calcularHCDeListaDeActividadesEnAnio(this.actividades.getListaDeActividades(),anio) + this.calcularHCEmpleados();
    }

    public Double calcularHCEnMes(Integer mes, Integer anio) throws Exception {
        return CalculoHC.calcularHCDeListaDeActividadesEnMes(this.actividades.getListaDeActividades(),mes,anio) + this.calcularHCEmpleados();
    }

    public Double calcularHCEmpleados(){
        //TODO
        return null;
    }
    
    public void llamar(String link) {
        this.contactos.forEach(contacto -> {
            try {
                contacto.notificar(link);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
