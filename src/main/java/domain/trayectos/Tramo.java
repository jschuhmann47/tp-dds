package domain.trayectos;

import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.Distancia;
import domain.transporte.MedioTransporte;

public class Tramo {
    MedioTransporte medioTransporte;
    Direccion puntoInicio;
    Direccion puntoFinal;

    Distancia distanciaARecorrer(Direccion puntoInicio, Direccion puntoFinal){
        return medioTransporte.calcularDistancia(puntoInicio,puntoFinal);
    }
}
