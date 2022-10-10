package models.entities.trayectos;

import models.entities.organizaciones.entidades.Organizacion;
import models.entities.organizaciones.entidades.Trabajador;
import models.entities.transporte.privado.TransportePrivado;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "tramo_compartido")
public class TramoCompartido {
    @Id
    @GeneratedValue
    private int id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tramo_compartido_organizacion",joinColumns = @JoinColumn(name = "tramo_compartido_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "organizacion_id",referencedColumnName = "id"))
    private final List<Organizacion> orgPosibles = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transporte_privado_id",referencedColumnName = "id")
    private TransportePrivado transportePrivado;

    @OneToMany
    @JoinColumn(name = "trabajador_id",referencedColumnName = "id")
    private final List<Trabajador> personasABordo = new ArrayList<>();

    public void agregarTrabajadorATramoCompartido(Trabajador trabajador) throws Exception {

        if(this.personasABordo.isEmpty()){
            this.agregarABordo(trabajador);
        }
        else{
            perteneceAAlgunaOrg(trabajador, this.orgPosibles);
        }
    }

    private void perteneceAAlgunaOrg(Trabajador trabajador,List<Organizacion> organizaciones) throws Exception {
        if (!Collections.disjoint(organizacionesDeUnaPersona(trabajador),organizaciones)){
            this.agregarABordo(trabajador);

        }else{
            throw new Exception("El trabajador ingresado no puede compartir este tramo con las personas ya cargadas " +
                    "al mismo, ya que no comparten ninguna organizacion");
        }
    }

    List<Organizacion> organizacionesDeUnaPersona(Trabajador trabajador){
        return trabajador.sectores.stream().map(s->s.organizacion).collect(Collectors.toList());
    }

    private void agregarABordo(Trabajador trabajador){
        this.personasABordo.add(trabajador);
        this.orgPosibles.addAll(organizacionesDeUnaPersona(trabajador));
    }

    public int cantidadDeTrabajadores(){
        return personasABordo.size();
    }


}
