package models.entities.organizaciones.entidades;

import models.entities.CargaDeActividades.CargaDeActividades;
import models.entities.CargaDeActividades.entidades.Actividad;
import models.entities.CargaDeActividades.entidades.Periodo;
import models.entities.calculoHC.CalculoHC;
import models.entities.geoDDS.Direccion;
import models.entities.organizaciones.contacto.Contacto;
import models.entities.organizaciones.solicitudes.Solicitud;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "organizacion")
public class Organizacion {
    @Id
    @GeneratedValue
    @Getter
    private int id;


    @ElementCollection
    @CollectionTable(name = "organizacion_clasificacion",joinColumns = @JoinColumn(name = "organizacion_id"))
    @Column(name = "clasificacion")
    @Getter
    private List<String> clasificacionOrg;



    @Column(name = "razon_social",nullable = false)
    @Getter
    private String razonSocial;

    @OneToMany(mappedBy = "organizacion",fetch = FetchType.LAZY,cascade = CascadeType.ALL) //todo dice organizacion (int) en esta tabla
    @Getter
    private List<Sector> sectores;

    @Enumerated(value = EnumType.STRING)
    private TipoOrganizacion tipoOrganizacion;

    @Getter
    @Embedded
    private Direccion direccion;


    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "organizacion_id",referencedColumnName = "id")
    private List<Actividad> listaDeActividades;

    @Setter
    @Getter
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "organizacion_id",referencedColumnName = "id")
    private List<Contacto> contactos;

//    @Getter
//    @Setter
//    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinColumn(name = "organizacion_id",referencedColumnName = "id")
//    private List<Solicitud> listaDeSolicitudes;

    public Organizacion(List<Trabajador> miembros, List<Sector> sectores) {
        this.sectores = sectores;
        this.listaDeActividades = new ArrayList<>();
    }

    public Organizacion(){
        this.listaDeActividades = new ArrayList<>();
    }

    public Organizacion(List<String> clasificacionOrg, List<Trabajador> miembros,
                        String razonSocial, List<Sector> sectores, TipoOrganizacion tipoOrganizacion,
                        Direccion direccion){

        this.clasificacionOrg = clasificacionOrg;

        this.razonSocial = razonSocial;
        this.sectores = sectores;
        this.tipoOrganizacion= tipoOrganizacion;
        this.direccion = direccion;
        this.listaDeActividades = new ArrayList<>();
    }


    public void cargarDatos(String archivo) throws IOException {
        CargaDeActividades.cargarActividadesDeArchivo(this.getListaDeActividades(),archivo);

    }

    public void agregarActividad(Actividad actividad){
        CargaDeActividades.cargarActividad(this.getListaDeActividades(),actividad);
    }

    public void agregarNuevoSector(Sector sector){
        sectores.add(sector);
    }




    public Double calcularHCEnPeriodo(Periodo periodo) {
        return this.calcularHCActividadesEnPeriodo(periodo) + this.calcularHCEmpleados(periodo);
    }

    public Double calcularHCActividadesEnPeriodo(Periodo periodo){
        return CalculoHC.calcularHCDeListaDeActividadesEnPeriodo(this.getListaDeActividades(),periodo);
    }


    public Double calcularHCEmpleados(Periodo periodo) {
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

    public Double calcularHCTotal(){
        return this.calcularHCTotalActividades() + this.calcularHCTotalTrabajadores();
    }
    public Double calcularHCTotalActividades(){
        return CalculoHC.calcularHCDeListaDeActividadesTotal(this.getListaDeActividades());
    }
    public Double calcularHCTotalTrabajadores(){
        return this.sectores.stream().mapToDouble(Sector::calcularHCTotalSector).sum();
    }

    public void notificarAContactos(String contenido) {
        this.contactos.forEach(contacto -> {
            contacto.notificar(contenido);
        });
    }

    public List<Solicitud> getListaDeSolicitudes() {
        return this.getSectores().stream().flatMap(sector -> sector.getSolicitudes().stream()).collect(Collectors.toList());
    }

    public Sector obtenerSectorPorNombre(String nombre){
        List<Sector> posibleSector = this.getSectores().stream()
                .filter(s -> Objects.equals(s.getNombreSector(), nombre))
                .collect(Collectors.toList());
        if(posibleSector.isEmpty()){
            throw new RuntimeException("No se encontro el sector " + nombre);
        }else{
            return posibleSector.get(0); //se asume unico
        }
    }
}
