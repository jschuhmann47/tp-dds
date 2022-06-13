package domain.trayectos;

import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.Distancia;

import java.util.Arrays;
import java.util.List;

public class Trayecto {
    private Direccion puntoDeSalida;
    private Direccion puntoDeLlegada;

    public List<Tramo> getTramos() {
        return tramos;
    }

    private List<Tramo> tramos;

    public void cargarTramos(Tramo ... tramos){
        this.tramos.addAll(Arrays.asList(tramos));
    }

    public Distancia distanciaTrayecto(){

        double valor = tramos.stream().map(tramo -> {
                    try {
                        return tramo.distanciaARecorrer(tramo.puntoInicio,tramo.puntoFinal).valor;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    })
                    .mapToDouble(i->i).sum(); //TODO
        return new Distancia(valor,"KM");
    }
}
