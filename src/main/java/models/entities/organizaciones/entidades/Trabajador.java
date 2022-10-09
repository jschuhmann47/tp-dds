package models.entities.organizaciones.entidades;

import lombok.Getter;
import models.entities.CargaDeActividades.entidades.Periodo;
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

    public void solicitarVinculacion(Organizacion organizacion,Sector sector){
        if(sector.getOrganizacion() != organizacion){
            throw new RuntimeException("El sector ingresado no es de la organizacion ingresada");
        }
        organizacion.solicitudDeVinculacion(this, sector);
    }

    public void solicitudAceptada(Sector sector){
        sectores.add(sector);
    }

    public List<Trayecto> getListaTrayectos() {
        return listaTrayectos;
    }

    public void setListaTrayectos(List<Trayecto> listaTrayectos) {
        this.listaTrayectos = listaTrayectos;
    }

    public void agregarTrayectos(Trayecto... trayectos){
        listaTrayectos.addAll(Arrays.asList(trayectos));
    }

    public Double calcularHC(Periodo periodo) throws Exception{ //
        return this.listaTrayectos.stream().mapToDouble(t-> {
            try {
                return t.calcularHC(periodo);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).sum();
    }
    public class TrabajadorDTO{
        String nombre;
        Double HCTotal;
        public TrabajadorDTO(Trabajador t){
            nombre = t.nombre;
            HCTotal = t.calcularHCTotal();
        }
    }

    public void agregarSector(Sector sector){
        this.getSectores().add(sector);
    }


    public Double calcularHCTotal() {
        return this.listaTrayectos.stream().mapToDouble(Trayecto::calcularHCTotal).sum();
    }
}
