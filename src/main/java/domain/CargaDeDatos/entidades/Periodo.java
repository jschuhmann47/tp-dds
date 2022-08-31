package domain.CargaDeDatos.entidades;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
public class Periodo {
    @Getter
    @Setter
    Integer mes;
    @Getter
    @Setter
    Integer anio;

    public Periodo() {
    }

    public Periodo(Integer mes, Integer anio) {
        this.mes = mes;
        this.anio = anio;
    }

}
