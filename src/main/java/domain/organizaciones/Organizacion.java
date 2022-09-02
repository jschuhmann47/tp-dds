package domain.organizaciones;

import domain.CargaDeActividades.CargaDeActividades;
import domain.CargaDeActividades.entidades.Actividad;
import domain.CargaDeActividades.entidades.Periodo;
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


    @ElementCollection
    @CollectionTable(name = "organizacion_clasificacion",joinColumns = @JoinColumn(name = "organizacion_id"))
    @Column(name = "clasificacion")
    private List<String> clasificacionOrg;



    @Column(name = "razon_social")
    @Getter
    private String razonSocial;

    @OneToMany(mappedBy = "organizacion")
    @Getter
    private List<Sector> sectores;

    @Enumerated(value = EnumType.STRING)
    private TipoOrganizacion tipoOrganizacion;

    @Getter
    @Embedded
    private Direccion ubicacion;


    @Getter
    @Setter
    @OneToMany
    @JoinColumn(name = "actividad_id",referencedColumnName = "id")
    private List<Actividad> listaDeActividades;

    @Setter
    @Getter
    @OneToMany
    @JoinColumn(name = "organizacion_id",referencedColumnName = "id")
    private List<Contacto> contactos;

    public Organizacion(List<Trabajador> miembros, List<Sector> sectores) {
        this.sectores = sectores;
    }

    public Organizacion(){
        this.listaDeActividades=new ArrayList<>();
    }


    public void cargarDatos(String archivo) throws IOException {
        CargaDeActividades.cargarActividades(this.getListaDeActividades(),archivo);
        this.getListaDeActividades().forEach(CalculoHC::calcularHCDeActividad);
    }

    public void agregarNuevoSector(Sector sector){
        sectores.add(sector);
    }

    public void solicitudDeVinculacion(Trabajador trabajador, Sector sector){
        trabajador.solicitudAceptada(sector);
        sector.agregarTrabajador(trabajador);
    }

    public Organizacion(List<String> clasificacionOrg, List<Trabajador> miembros,
                        String razonSocial, List<Sector> sectores, TipoOrganizacion tipoOrganizacion,
                        Direccion ubicacion){

        this.clasificacionOrg = clasificacionOrg;

        this.razonSocial = razonSocial;
        this.sectores = sectores;
        this.tipoOrganizacion= tipoOrganizacion;
        this.ubicacion = ubicacion;
        this.listaDeActividades=new ArrayList<>();

    }

    public Double calcularHC(Periodo periodo) throws Exception {
        return CalculoHC.calcularHCDeListaDeActividades(this.getListaDeActividades(),periodo) + this.calcularHCEmpleados(periodo);
    }

    public Double calcularHCEmpleados(Periodo periodo) { //TODO cambiar en el uml
        return this.getSectores().stream().mapToDouble(s->s.calcularHCSector(periodo)).sum();
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
