package domain.transporte;

import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.Distancia;

public interface MedioTransporte {

    String detalle();

    Distancia calcularDistancia(Direccion origen, Direccion destino);

}
