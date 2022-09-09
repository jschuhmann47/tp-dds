package domain.reportes;

import domain.geoDDS.entidades.Provincia;
import lombok.Getter;
import lombok.Setter;

public class Composicion {

    @Setter
    @Getter
    Provincia provincia;
    String nombre;
    @Getter
    Double porcentaje;

    public Composicion(String nombre, Double porcentaje) {
        this.nombre = nombre;
        this.porcentaje = porcentaje;
    }
}
