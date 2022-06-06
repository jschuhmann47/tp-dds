package domain.trayectos;

import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.Distancia;
import domain.transporte.MedioTransporte;

public class Tramo {
    public MedioTransporte medioTransporte;
    public Direccion puntoInicio;
    public Direccion puntoFinal;

    public Distancia distanciaARecorrer(Direccion puntoInicio, Direccion puntoFinal) throws Exception {
        return medioTransporte.calcularDistancia(puntoInicio,puntoFinal);
    }
}
