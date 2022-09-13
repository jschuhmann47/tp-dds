package domain.organizaciones.entidades;

import domain.CargaDeActividades.CargaDeActividades;
import domain.CargaDeActividades.entidades.Actividad;
import domain.CargaDeActividades.entidades.Periodo;
import domain.calculoHC.CalculoHC;
import domain.geoDDS.Direccion;
import domain.organizaciones.contacto.Contacto;
import domain.organizaciones.solicitudes.Solicitud;
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

    @Getter
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "organizacion_id",referencedColumnName = "id")
    private List<Solicitud> listaDeSolicitudes;

    public Organizacion(List<Trabajador> miembros, List<Sector> sectores) {
        this.sectores = sectores;
        this.listaDeActividades = new ArrayList<>();
        this.listaDeSolicitudes = new ArrayList<>();
    }

    public Organizacion(){
        this.listaDeActividades = new ArrayList<>();
        this.listaDeSolicitudes = new ArrayList<>();
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
        this.listaDeSolicitudes = new ArrayList<>();

    }


    public void cargarDatos(String archivo) throws IOException {
        CargaDeActividades.cargarActividades(this.getListaDeActividades(),archivo);
        this.getListaDeActividades().forEach(CalculoHC::calcularHCDeActividad);
    }

    public void agregarNuevoSector(Sector sector){
        sectores.add(sector);
    }

    public void solicitudDeVinculacion(Trabajador trabajador, Sector sector){
        Solicitud solicitudNueva = new Solicitud(sector,trabajador);
        this.getListaDeSolicitudes().add(solicitudNueva);
    }

    public void aceptarSolicitud(Solicitud solicitud){
        solicitud.aceptarSolicitud();
    }
    public void rechazarSolicitud(Solicitud solicitud){
        solicitud.rechazarSolicitud();
    }


    public Double calcularHCEnPeriodo(Periodo periodo) throws Exception {
        return CalculoHC.calcularHCDeListaDeActividadesEnPeriodo(this.getListaDeActividades(),periodo) + this.calcularHCEmpleados(periodo);
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

}
