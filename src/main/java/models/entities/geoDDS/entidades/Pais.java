package models.entities.geoDDS.entidades;


import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pais")
public class Pais{
    @Getter
    @Id
    public int id;

    @Getter
    @Column(name = "nombre",nullable = false)
    public String nombre;

    public Pais(){

    }

    public Pais (int id, String nombre){
        this.id=id;
        this.nombre=nombre;
    }
}
