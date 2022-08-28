package domain.organizaciones;

import domain.CargaDeDatos.CargaDeDatos;
import domain.CargaDeDatos.entidades.Periodo;
import domain.calculoHC.CalculoHC;
import domain.geoDDS.Direccion;
import domain.organizaciones.contacto.Contacto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "organizacion")
public class Organizacion {
    @Id
    @GeneratedValue
    private int id;

    //crear una entidad?
    @Transient
    private ClasificacionOrganizacion clasificacionOrg;

    @Transient
    private List<Trabajador> miembros; //estan en sector, los volaria

    @Column(name = "razon_social")
    private String razonSocial;

    @OneToMany(mappedBy = "organizacion")
    private List<Sector> sectores;

    @Column(name = "tipo_organizacion")
    private TipoOrganizacion tipoOrganizacion;

    @Getter
    @Transient
    private Direccion ubicacion; //TODO

    @Setter
    @Transient
    private CargaDeDatos actividades;

    @Setter
    @Getter
    @OneToMany
    @JoinColumn(name = "organizacion_id",referencedColumnName = "id")
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
