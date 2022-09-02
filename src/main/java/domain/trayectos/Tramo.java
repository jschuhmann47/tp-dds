package domain.trayectos;

import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.Distancia;
import domain.transporte.MedioTransporte;

import javax.persistence.*;

@Entity
@Table(name = "tramo")
public class Tramo {
    @Id
    @GeneratedValue
    private int id;

    @Transient
    public MedioTransporte medioTransporte; //todo interfaz
    @Embedded
    @AttributeOverrides({@AttributeOverride(name="altura",column = @Column(name = "altura_inicio")),
            @AttributeOverride(name = "calle",column = @Column(name = "calle_inicio")),
            @AttributeOverride(name = "localidad",column = @Column(name = "localidad_inicio_id"))})
    public Direccion puntoInicio;
    @Embedded
    @AttributeOverrides({@AttributeOverride(name="altura",column = @Column(name = "altura_fin")),
            @AttributeOverride(name = "calle",column = @Column(name = "calle_fin")),
            @AttributeOverride(name = "localidad",column = @Column(name = "localidad_fin_id"))})
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
