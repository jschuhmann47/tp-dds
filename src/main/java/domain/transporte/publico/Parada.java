package domain.transporte.publico;

import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.Distancia;

public class Parada {
    Parada paradaSiguiente;
    Distancia distanciaAnterior;
    Distancia distanciaSiguiente;
    Direccion direccion;
}
