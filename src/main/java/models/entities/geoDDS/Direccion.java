package models.entities.geoDDS;

import models.entities.geoDDS.entidades.Localidad;
import models.entities.geoDDS.entidades.Municipio;
import models.entities.geoDDS.entidades.Provincia;

import javax.persistence.*;

@Embeddable
public class Direccion {
    @Column(name = "altura",nullable = false)
    private Integer altura;
    @Column(name = "calle",nullable = false)
    private String calle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "localidad_id",referencedColumnName = "id")
    private Localidad localidad;


    public Direccion() {
    }

    public Direccion(Integer altura, String calle, Localidad localidad) {
        this.altura = altura;
        this.calle = calle;
        this.localidad = localidad;
    }


    public Municipio getMunicipio() {
        return this.localidad.getMunicipio();
    }


    public Provincia getProvincia() {
        return localidad.getMunicipio().getProvincia();
    }


    public Integer getAltura() {
        return altura;
    }


    public String getCalle() {
        return calle;
    }

    public Localidad getLocalidad() {
        return localidad;
    }


}

