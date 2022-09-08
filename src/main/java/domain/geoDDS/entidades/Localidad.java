package domain.geoDDS.entidades;

import javax.persistence.*;

@Entity
@Table(name = "localidad")
public class Localidad {

    @Id
    public int id;

    @Column(name = "nombre",nullable = false)
    public String nombre;
    @Column(name = "codigo_postal",nullable = false)
    public int codPostal;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
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

    public int getId() {
        return this.id;
    }

    public Municipio getMunicipio() {
        return this.municipio;
    }
}
