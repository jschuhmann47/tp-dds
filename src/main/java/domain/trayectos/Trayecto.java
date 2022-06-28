package domain.trayectos;

import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.Distancia;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Trayecto {
    private Direccion puntoDeSalida;
    private Direccion puntoDeLlegada;

    public Trayecto(Direccion puntoDeSalida, Direccion puntoDeLlegada, List<Tramo> tramos) {
        this.puntoDeSalida = puntoDeSalida;
        this.puntoDeLlegada = puntoDeLlegada;
        this.tramos = tramos;
    }

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

    public Double calcularHCAnual(Integer anio) throws Exception {
        return this.tramos.stream()
                .filter(t-> Objects.equals(t.getAnio(), anio))
                .mapToDouble(t-> {
                    try {
                        return t.calcularHC();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .sum();
    }

    public Double calcularHCMensual(Integer mes, Integer anio) throws Exception{
        return this.tramos.stream()
                .filter(t-> Objects.equals(t.getAnio(), anio) && Objects.equals(t.getMes(), mes))
                .mapToDouble(t-> {
                    try {
                        return t.calcularHC();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .sum();
    }
}
