package domain.trayectos;

import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.Distancia;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Trayecto {
    private Direccion puntoDeSalida;
    private Direccion puntoDeLlegada;
    public Frecuencia frecuencia;



    Distancia distanciaTrayecto;

    public Trayecto(Direccion puntoDeSalida, Direccion puntoDeLlegada, List<Tramo> tramos,Frecuencia frecuencia) {
        this.puntoDeSalida = puntoDeSalida;
        this.puntoDeLlegada = puntoDeLlegada;
        this.tramos = tramos;
        this.frecuencia = frecuencia;
        distanciaTrayecto = this.distanciaTrayecto();
    }

    public Distancia getDistanciaTrayecto() {
        return distanciaTrayecto;
    }

    public List<Tramo> getTramos() {
        return tramos;
    }

    private List<Tramo> tramos;

    public void cargarTramos(Tramo ... tramos){
        this.tramos.addAll(Arrays.asList(tramos));
    }

    public Distancia distanciaTrayecto(){ //private? y el de tramo tmb

        double valor = tramos.stream().map(tramo -> {
                    try {
                        return tramo.getDistancia().valor;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    })
                    .mapToDouble(i->i).sum(); //TODO
        return new Distancia(valor,"KM");
    }

    public Double calcularHCAnual(Integer anio) {
        return this.tramos.stream()
                .filter(t-> Objects.equals(t.getAnio(), anio))
                .mapToDouble(t-> {
                    try {
                        return t.calcularHC()*this.frecuencia.vecesPorMes()*12; //TODO pregunta
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .sum()*this.frecuencia.vecesPorMes();
    }

    public Double calcularHCMensual(Integer mes, Integer anio) {
        return this.tramos.stream()
                .filter(t-> Objects.equals(t.getAnio(), anio) && Objects.equals(t.getMes(), mes))
                .mapToDouble(t-> {
                    try {
                        return t.calcularHC()*this.frecuencia.vecesPorMes();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .sum()*this.frecuencia.vecesPorMes();
    }
}
