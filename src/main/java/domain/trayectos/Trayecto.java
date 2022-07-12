package domain.trayectos;

import domain.CargaDeDatos.entidades.Periodo;
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

    public Double calcularHC(Periodo periodo) {
        return this.tramos.stream()
                .mapToDouble(t-> {
                    try {
                        return t.calcularHC()*this.frecuencia.vecesPorMes(periodo);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .sum();
    }


}
