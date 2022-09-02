package domain.CargaDeActividades.entidades;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Periodo {
    @Getter
    @Setter
    @Column(name = "mes",nullable = true)
    Integer mes;
    @Getter
    @Setter
    @Column(name = "anio")
    Integer anio;

    public Periodo() {
    }

    public Periodo(Integer mes, Integer anio) {
        this.mes = mes;
        this.anio = anio;
    }

}
