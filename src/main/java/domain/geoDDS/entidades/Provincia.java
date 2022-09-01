package domain.geoDDS.entidades;

import javax.persistence.*;

@Entity
@Table(name = "provincia")
public class Provincia {

    @Id
    public int id;

    @Column(name = "nombre")
    public String nombre;

    @OneToOne
    @JoinColumn(name = "pais_id",referencedColumnName = "id")
    public Pais pais;

    public Provincia(){

    }
    public Provincia(int idP, String nombreP, Pais paisP){
        this.id = idP;
        this.nombre = nombreP;
        this.pais=paisP;
    }

    public int getId() {
        return id;
    }
}
