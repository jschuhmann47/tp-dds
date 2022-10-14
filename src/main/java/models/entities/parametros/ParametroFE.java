package models.entities.parametros;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "parametro_fe") //TODO mejorar esto
public class ParametroFE {
    @Getter
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "nombre")
    public String nombre;
    @Column(name = "valor")
    public Double valor;
}
