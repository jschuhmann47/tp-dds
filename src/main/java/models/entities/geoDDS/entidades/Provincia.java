package models.entities.geoDDS.entidades;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "provincia")
public class Provincia {
    @Getter
    @Id
    public int id;

    @Column(name = "nombre", nullable = false)
    @Getter
    public String nombre;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "pais_id",referencedColumnName = "id")
    public Pais pais;

    public Provincia(){

    }
    public Provincia(int idP, String nombreP, Pais paisP){
        this.id = idP;
        this.nombre = nombreP;
        this.pais=paisP;
    }
}
