package domain.trayectos;

import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.Distancia;

import java.util.Arrays;
import java.util.List;

public class Trayecto {
    private Direccion puntoDeSalida;
    private Direccion puntoDeLlegada;
    private List<Tramo> tramos;

    public void cargarTramos(Tramo ... tramos){
        this.tramos.addAll(Arrays.asList(tramos));
    }

    public Distancia distanciaTrayecto(){
        tramos.stream().map(tramo -> tramo.distanciaARecorrer(tramo.puntoInicio,tramo.puntoFinal).convertirValor()); //TODO
    }
}
