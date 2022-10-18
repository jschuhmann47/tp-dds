package models.entities.geoDDS.entidades;


import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Distancia {
    @Column(name = "distancia_valor",nullable = false)
    public Double valor;
    @Column(name = "distancia_unidad",nullable = false)
    public String unidad;

    public Distancia() {
    }

    public Distancia(Double valor, String unidad) {
        this.valor = valor;
        this.unidad = unidad;
    }

}
