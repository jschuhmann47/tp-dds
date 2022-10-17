package models.entities.organizaciones.entidades;

import lombok.Getter;
import lombok.Setter;
import models.entities.CargaDeActividades.entidades.Periodo;
import models.entities.organizaciones.solicitudes.Solicitud;
import models.entities.trayectos.Trayecto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "trabajador")
public class Trabajador {
    @Id
    @GeneratedValue
    private int id;

    @Getter
    @Column(name = "apellido",nullable = false)
    private String apellido;

    @Getter
    @Column(name = "nombre",nullable = false)
    private String nombre;
    @Getter
    @Enumerated(value = EnumType.STRING)
    private TipoDoc tipoDoc;
    @Getter
    @Column(name = "nro_doc",nullable = false)
    private Integer nroDoc;

    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "trabajador_id",referencedColumnName = "id")
    private List<Trayecto> listaTrayectos = new ArrayList<>();

    @Getter
    @ManyToMany(mappedBy = "trabajadores") //todo queda trabajador_id en la tabla
    public List<Sector> sectores = new ArrayList<>();

    @Getter
    @Setter
    @OneToMany(mappedBy = "trabajador")
    private List<Solicitud> listaDeSolicitudes = new ArrayList<>();

    public Trabajador(String apellido, String nombre, TipoDoc tipoDoc, Integer nroDoc) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.tipoDoc = tipoDoc;
        this.nroDoc = nroDoc;
    }

    public Trabajador(){
    }

    public int getId() {
        return id;
    }

    public Solicitud solicitarVinculacion(Organizacion organizacion,Sector sector){
        if(sector.getOrganizacion() != organizacion){
            throw new RuntimeException("El sector ingresado no es de la organizacion ingresada");
        }
        Solicitud nuevaSol = this.crearSolicitud(sector);
        sector.solicitudDeVinculacion(nuevaSol);
        return nuevaSol;
    }

    private Solicitud crearSolicitud(Sector sector){
        Solicitud solicitud = new Solicitud(sector,this);
        this.getListaDeSolicitudes().add(solicitud);
        return solicitud;
    }

    private void solicitudAceptada(Sector sector){
        sectores.add(sector);
    }

    public List<Trayecto> getListaTrayectos() {
        return listaTrayectos;
    }

    public void setListaTrayectos(List<Trayecto> listaTrayectos) {
        this.listaTrayectos = listaTrayectos;
    }


    public void agregarTrayectos(Trayecto ... trayectos){
        listaTrayectos.addAll(Arrays.asList(trayectos));
    }

    public Double calcularHC(Periodo periodo){ //
        return this.listaTrayectos.stream()
                .mapToDouble(t-> t.calcularHC(periodo))
                .sum();
    }

    public void agregarSector(Sector sector){
        this.getSectores().add(sector);
    }

    public Double calcularHCTotal() {
        return this.listaTrayectos.stream().mapToDouble(Trayecto::calcularHCTotal).sum();
    }
}
