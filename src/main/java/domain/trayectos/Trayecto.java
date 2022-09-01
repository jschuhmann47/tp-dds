package domain.trayectos;

import domain.CargaDeDatos.entidades.Periodo;
import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.Distancia;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "trayecto")
public class Trayecto {
    @Id
    @GeneratedValue
    private int id;

    @Transient
    private Direccion puntoDeSalida;
    @Transient
    private Direccion puntoDeLlegada;
    @Embedded
    public Frecuencia frecuencia; //traerlo aca

    @Transient
    private List<Tramo> tramos;

    @Transient
    Distancia distanciaTrayecto;

    public Trayecto() {
    }

    public Trayecto(Direccion puntoDeSalida, Direccion puntoDeLlegada, List<Tramo> tramos, Frecuencia frecuencia) {
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
