package models.entities.parametros;

import lombok.Getter;
import lombok.Setter;
import models.entities.CargaDeActividades.entidades.TipoDeConsumo;

import javax.persistence.*;

@Entity
@Table(name = "parametro_fe")
public class ParametroFE {
    @Getter
    @Id
    @GeneratedValue
    private int id;

    @Getter
    @Column(name = "nombre")
    private String nombre;

    @Getter
    @Setter
    @Column(name = "valor")
    private Double valor;

    public ParametroFE(String nombre, Double valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    public ParametroFE(){

    }
}
