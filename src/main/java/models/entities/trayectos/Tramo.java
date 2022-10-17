package models.entities.trayectos;

import lombok.Getter;
import models.entities.geoDDS.Direccion;
import models.entities.geoDDS.entidades.Distancia;
import models.entities.transporte.MedioTransporte;

import javax.persistence.*;

@Entity
@Table(name = "tramo")
public class Tramo {
    @Id
    @GeneratedValue
    private int id;

    @Getter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medio_transporte_id",referencedColumnName = "id")
    public MedioTransporte medioTransporte;
    @Enumerated(value = EnumType.STRING)

    @Getter
    @Embedded
    @AssociationOverride(name = "localidad",joinColumns = @JoinColumn(name = "localidad_inicio_id"))
    @AttributeOverrides({@AttributeOverride(name="altura",column = @Column(name = "altura_inicio")),
            @AttributeOverride(name = "calle",column = @Column(name = "calle_inicio"))})
    public Direccion puntoInicio;

    @Getter
    @Embedded
    @AssociationOverride(name = "localidad",joinColumns = @JoinColumn(name = "localidad_fin_id"))
    @AttributeOverrides({@AttributeOverride(name="altura",column = @Column(name = "altura_fin")),
            @AttributeOverride(name = "calle",column = @Column(name = "calle_fin"))})
    public Direccion puntoFinal;
    @Embedded
    public Distancia distanciaTramo;


    public Tramo() {
    }

    public Tramo(MedioTransporte medioTransporte, Direccion puntoInicio, Direccion puntoFinal) throws Exception {
        this.medioTransporte = medioTransporte;
        this.puntoInicio = puntoInicio;
        this.puntoFinal = puntoFinal;
        this.distanciaTramo = medioTransporte.calcularDistancia(puntoInicio,puntoFinal);
    }


    public Distancia getDistancia() {
        return this.distanciaTramo;
    }


    public Double calcularHC() {
        return medioTransporte.calcularHC(this.distanciaTramo);
    }
}
