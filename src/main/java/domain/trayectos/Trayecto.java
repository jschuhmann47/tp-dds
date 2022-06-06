package domain.trayectos;

import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.Distancia;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Trayecto {
    private Direccion puntoDeSalida;
    private Direccion puntoDeLlegada;
    private List<Tramo> tramos;

    public void cargarTramos(Tramo ... tramos){
        this.tramos.addAll(Arrays.asList(tramos));
    }

    public Distancia distanciaTrayecto(){

        Double valor = tramos.stream().map(tramo -> tramo.distanciaARecorrer(tramo.puntoInicio,tramo.puntoFinal).convertirValor())
                        .mapToDouble(i->i).sum(); //TODO
        return new Distancia(valor.toString(),"KM");
    }
}
