package models.entities.geoDDS.entidades;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "municipio")
public class Municipio {
    @Getter
    @Id
    public int id;

    @Column(name = "nombre",nullable = false)
    @Getter
    public String nombre;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provincia_id",referencedColumnName = "id")
    public Provincia provincia;

    public Municipio(){

    }

    public Municipio(int id, String nombre, Provincia provincia) {
        this.id = id;
        this.nombre = nombre;
        this.provincia = provincia;
    }
}
