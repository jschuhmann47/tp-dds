package domain.geoDDS.entidades;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pais")
public class Pais{
    @Id
    public int id;

    @Column(name = "nombre")
    public String nombre;

    public Pais(){

    }

    public Pais (int id, String nombre){
        this.id=id;
        this.nombre=nombre;
    }
}
