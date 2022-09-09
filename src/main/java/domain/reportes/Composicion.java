package domain.reportes;

import domain.geoDDS.entidades.Provincia;
import lombok.Setter;

public class Composicion {

    @Setter
    Provincia provincia;
    String nombre;
    Double porcentaje;

    public Composicion(String nombre, Double porcentaje) {
        this.nombre = nombre;
        this.porcentaje = porcentaje;
    }
}
