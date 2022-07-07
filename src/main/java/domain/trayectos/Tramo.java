package domain.trayectos;

import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.Distancia;
import domain.transporte.MedioTransporte;

public class Tramo {
    public MedioTransporte medioTransporte;
    public Direccion puntoInicio;
    public Direccion puntoFinal;
    public Distancia distanciaTramo = null;

    //actividadDA ?

    public Tramo(MedioTransporte medioTransporte, Direccion puntoInicio, Direccion puntoFinal) {
        this.medioTransporte = medioTransporte;
        this.puntoInicio = puntoInicio;
        this.puntoFinal = puntoFinal;
    }

    public Distancia distanciaARecorrer(Direccion puntoInicio, Direccion puntoFinal) throws Exception {
        this.distanciaTramo = medioTransporte.calcularDistancia(puntoInicio,puntoFinal);
        return this.distanciaTramo;
    }

    public Double getConsumoPorKM(){
        return medioTransporte.getConsumoPorKM();
    }

    private Double getDistancia() throws Exception {
        if(distanciaTramo == null){
            throw new Exception("La distancia no fue calculada.");
        }
        else return this.distanciaTramo.valor;
    }

    public Integer getAnio() {
        //TODO
        return null;
    }

    public Integer getMes() {
        //TODO
        return null;
    }
}
