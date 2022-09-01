package domain.geoDDS.entidades;


import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Distancia {
    @Column(name = "distancia_valor")
    public Double valor;
    @Column(name = "distancia_unidad")
    public String unidad;

    public Distancia() {
    }

    public Distancia(Double valor, String unidad) {
        this.valor = valor;
        this.unidad = unidad;
    }

}
