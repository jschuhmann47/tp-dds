package domain.geoDDS.entidades;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "municipio")
public class Municipio {
    @Id
    public int id;

    @Column(name = "nombre",nullable = false)
    public String nombre;

    @Getter
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "provincia_id",referencedColumnName = "id")
    public Provincia provincia;

    public Municipio(){

    }

    public Municipio(int id, String nombre, Provincia provincia) {
        this.id = id;
        this.nombre = nombre;
        this.provincia = provincia;
    }

    public int getId() {
        return this.id;
    }
}
