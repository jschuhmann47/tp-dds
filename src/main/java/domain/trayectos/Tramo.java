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
    public MedioTransporte medioTransporte; //interfaz
    @Transient
    public Direccion puntoInicio;
    @Transient
    public Direccion puntoFinal;
    @Transient
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
