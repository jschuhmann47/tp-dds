package domain.geoDDS.entidades;

import javax.persistence.*;

@Entity
@Table(name = "localidad")
public class Localidad {

    @Id
    public int idLocalidad;

    @Column(name = "nombre")
    public String nombre;
    @Column(name = "codigo_postal")
    public int codPostal;

    @OneToOne
    @JoinColumn(name = "municipio_id",referencedColumnName = "id")
    public Municipio municipio;

    public Localidad() {
    }

    public Localidad(int idLocalidad, String nombre, int codPostal, Municipio municipio) {
        this.idLocalidad = idLocalidad;
        this.nombre = nombre;
        this.codPostal = codPostal;
        this.municipio = municipio;
    }

    public int getIdLocalidad() {
        return this.idLocalidad;
    }

    public Municipio getMunicipio() {
        return this.municipio;
    }
}
