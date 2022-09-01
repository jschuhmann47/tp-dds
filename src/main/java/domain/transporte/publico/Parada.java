package domain.transporte.publico;

import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.Distancia;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "parada")
public class Parada {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "parada_siguiente")
    @Setter
    @Transient
    Parada paradaSiguiente;

    @Transient
    Distancia distanciaAnterior;
    @Transient
    Distancia distanciaSiguiente;
    @Transient
    Direccion direccion;

    public Parada() {
    }

    public Parada(Distancia distanciaAnterior, Distancia distanciaSiguiente, Direccion direccion) {
        this.distanciaAnterior = distanciaAnterior;
        this.distanciaSiguiente = distanciaSiguiente;
        this.direccion = direccion;
    }

}
