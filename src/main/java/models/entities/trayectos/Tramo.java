package models.entities.trayectos;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medio_transporte_id",referencedColumnName = "id")
    public MedioTransporte medioTransporte;
    @Enumerated(value = EnumType.STRING)

    @Embedded
    @AssociationOverride(name = "localidad",joinColumns = @JoinColumn(name = "localidad_inicio_id"))
    @AttributeOverrides({@AttributeOverride(name="altura",column = @Column(name = "altura_inicio")),
            @AttributeOverride(name = "calle",column = @Column(name = "calle_inicio"))})
    public Direccion puntoInicio;
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
        this.distanciaTramo = this.distanciaARecorrer(puntoInicio,puntoFinal);
    }

    public Distancia distanciaARecorrer(Direccion puntoInicio, Direccion puntoFinal) throws Exception {
        this.distanciaTramo = medioTransporte.calcularDistancia(puntoInicio,puntoFinal);
        return this.distanciaTramo;
    }

    public Distancia getDistancia() {
        return this.distanciaTramo;
    }


    public Double calcularHC() {
        return medioTransporte.calcularHC(this.distanciaTramo);
    }
}
