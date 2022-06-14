package domain.transporte.publico;

import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.Distancia;
import lombok.Setter;


public class Parada {
    @Setter
    Parada paradaSiguiente;

    Distancia distanciaAnterior;
    Distancia distanciaSiguiente;
    Direccion direccion;

    public Parada(Distancia distanciaAnterior, Distancia distanciaSiguiente, Direccion direccion) {
        this.distanciaAnterior = distanciaAnterior;
        this.distanciaSiguiente = distanciaSiguiente;
        this.direccion = direccion;
    }

}
