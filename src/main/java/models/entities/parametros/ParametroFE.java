package models.entities.parametros;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "parametro_fe") //TODO mejorar esto
public class ParametroFE {
    @Column(name = "nombre")
    public String nombre;
    @Column(name = "valor")
    public Double valor;
}
