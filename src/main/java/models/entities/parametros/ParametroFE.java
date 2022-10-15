package models.entities.parametros;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "parametro_fe") //TODO mejorar esto
public class ParametroFE {
    @Getter
    @Id
    @GeneratedValue
    private int id;

    @Getter
    @Column(name = "nombre")
    public String nombre;

    @Getter
    @Setter
    @Column(name = "valor")
    public Double valor;

    public ParametroFE(String nombre, Double valor) {
        this.nombre = nombre;
        this.valor = valor;
    }
}
