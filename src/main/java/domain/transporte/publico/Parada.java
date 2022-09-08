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
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parada_siguiente_id", referencedColumnName = "id")
    Parada paradaSiguiente;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name="valor",column = @Column(name = "distancia_valor_parada_anterior")),
                        @AttributeOverride(name = "unidad",column = @Column(name = "distancia_unidad_parada_anterior"))})
    Distancia distanciaAnterior;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name="valor",column = @Column(name = "distancia_valor_parada_siguiente")),
                        @AttributeOverride(name = "unidad",column = @Column(name = "distancia_unidad_parada_siguiente"))})
    Distancia distanciaSiguiente;

    @Embedded
    Direccion direccion;

    public Parada() {
    }

    public Parada(Distancia distanciaAnterior, Distancia distanciaSiguiente, Direccion direccion) {
        this.distanciaAnterior = distanciaAnterior;
        this.distanciaSiguiente = distanciaSiguiente;
        this.direccion = direccion;
    }

}
