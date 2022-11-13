package models.entities.geoDDS.entidades;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "localidad")
@Getter
public class Localidad {
    @Getter
    @Id
    public int id;

    @Column(name = "nombre",nullable = false)
    public String nombre;
    @Column(name = "codigo_postal",nullable = false)
    public int codPostal;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "municipio_id",referencedColumnName = "id")
    public Municipio municipio;

    public Localidad() {
    }

    public Localidad(int idLocalidad, String nombre, int codPostal, Municipio municipio) {
        this.id = idLocalidad;
        this.nombre = nombre;
        this.codPostal = codPostal;
        this.municipio = municipio;
    }

}
