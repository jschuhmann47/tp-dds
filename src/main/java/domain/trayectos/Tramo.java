package domain.trayectos;

import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.Distancia;
import domain.transporte.MedioTransporte;

public class Tramo {
    public MedioTransporte medioTransporte;
    public Direccion puntoInicio;
    public Direccion puntoFinal;
    public Distancia distanciaTramo;

    //actividadDA ?

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

//    public Double getConsumoPorKM(){
//        return medioTransporte.getConsumoPorKM();
//    }

    public Distancia getDistancia() throws Exception {
        return this.distanciaTramo;
    }

    public Integer getAnio() {
        //TODO
        return null;
    }

    public Integer getMes() {
        //TODO
        return null;
    }

    public Double calcularHC() {
        return medioTransporte.calcularHC(this.distanciaTramo);
    }
}
