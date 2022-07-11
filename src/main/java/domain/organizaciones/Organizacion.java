package domain.organizaciones;

import domain.CargaDeDatos.CargaDeDatos;
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

    public Double calcularHCEnAnio(Integer anio) throws Exception {
        return CalculoHC.calcularHCDeListaDeActividadesEnAnio(this.actividades.getListaDeActividades(),anio) + this.calcularHCEmpleadosEnAnio(anio);
    }

    public Double calcularHCEnMes(Integer mes, Integer anio) throws Exception {
        return CalculoHC.calcularHCDeListaDeActividadesEnMes(this.actividades.getListaDeActividades(),mes,anio) + this.calcularHCEmpleadosEnMes(mes,anio);
    }

    public Double calcularHCEmpleadosEnAnio(Integer anio) throws Exception{
        return this.miembros.stream().mapToDouble(m-> {
            try {
                return m.calcularHCAnual();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).sum();
    }

    public Double calcularHCEmpleadosEnMes(Integer mes,Integer anio) throws Exception{
        return this.miembros.stream().mapToDouble(m-> {
            try {
                return m.calcularHCMensual();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).sum();
    }

    public Double huellaCarbonoEnSectorMensual(Sector sector){
        return sector.trabajadores.stream().mapToDouble(t-> {
            try {
                return t.calcularHCMensual();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).sum();
    }

    public Double huellaCarbonoEnSectorAnual(Sector sector){
        return sector.trabajadores.stream().mapToDouble(t-> {
            try {
                return t.calcularHCAnual();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).sum();
    }

    public List<String> huellaCarbonoPorCadaSectorMensual(){
        List<String> detalles = new ArrayList<>();
        sectores.forEach(s->{
            String detalle = "Sector: " + s.nombreSector + " - " + "Huella de carbono: " + this.huellaCarbonoEnSectorMensual(s);
            detalles.add(detalle);
        });
        return detalles;
    }

    public List<String> huellaCarbonoPorCadaSectorAnual(){
        List<String> detalles = new ArrayList<>();
        sectores.forEach(s->{
            String detalle = "Sector: " + s.nombreSector + " - " + "Huella de carbono: " + this.huellaCarbonoEnSectorAnual(s);
            detalles.add(detalle);
        });
        return detalles;
    }

    public void notificarAContactos(String contenido) {
        this.contactos.forEach(contacto -> {
            try {
                contacto.notificar(contenido);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
