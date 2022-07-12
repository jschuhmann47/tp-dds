package domain.organizaciones;

import domain.CargaDeDatos.CargaDeDatos;
import domain.CargaDeDatos.entidades.Periodo;
import domain.calculoHC.CalculoHC;
import domain.geoDDS.Direccion;
import domain.organizaciones.contacto.Contacto;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Organizacion {
    private ClasificacionOrganizacion clasificacionOrg;
    private List<Trabajador> miembros;
    private String razonSocial;
    private List<Sector> sectores;
    private TipoOrganizacion tipoOrganizacion;

    @Getter
    private Direccion ubicacion;

    @Setter
    private CargaDeDatos actividades;

    @Setter
    @Getter
    private List<Contacto> contactos;

    public Organizacion(List<Trabajador> miembros, List<Sector> sectores) {
        this.miembros = miembros;
        this.sectores = sectores;
    }

    public Organizacion(){

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

    public Double calcularHC(Periodo periodo) throws Exception {
        return CalculoHC.calcularHCDeListaDeActividades(this.actividades.getListaDeActividades(),periodo) + this.calcularHCEmpleados(periodo);
    }

    public Double calcularHCEmpleados(Periodo periodo) throws Exception{
        return this.miembros.stream().mapToDouble(m-> {
            try {
                return m.calcularHC(periodo);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).sum();
    }

    public Double huellaCarbonoEnSector(Sector sector, Periodo periodo){
        return sector.trabajadores.stream().mapToDouble(t-> {
            try {
                return t.calcularHC(periodo);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).sum();
    }

    public List<String> huellaCarbonoPorCadaSector(Periodo periodo){
        List<String> detalles = new ArrayList<>();
        sectores.forEach(s->{
            String detalle = "Sector: " + s.nombreSector + " - " + "Huella de carbono: " + this.huellaCarbonoEnSector(s,periodo);
            detalles.add(detalle);
        });
        return detalles;
    }

    public void notificarAContactos(String contenido) {
        this.contactos.forEach(contacto -> {
            contacto.notificar(contenido);
        });
    }

}
