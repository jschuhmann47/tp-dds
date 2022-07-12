package domain.CargaDeDatos.entidades;

import lombok.Getter;
import lombok.Setter;

public class Periodo {
    @Getter
    @Setter
    Integer mes;
    @Getter
    @Setter
    Integer anio;

    public Periodo(Integer mes, Integer anio) {
        this.mes = mes;
        this.anio = anio;
    }
}
