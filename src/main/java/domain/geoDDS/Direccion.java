package domain.geoDDS;

import domain.geoDDS.entidades.Localidad;
import domain.geoDDS.entidades.Municipio;
import domain.geoDDS.entidades.Provincia;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class Direccion {
    @Column(name = "altura")
    private Integer altura;
    @Column(name = "calle")
    private String calle;
    @Transient
    private Localidad localidad; //TODO

//    private Municipio municipio;
//    private Provincia provincia; //revisar

    public Direccion() {
    }

    public Direccion(Integer altura, String calle, Localidad localidad, Municipio municipio, Provincia provincia) {
        this.altura = altura;
        this.calle = calle;
        this.localidad = localidad;
//        this.municipio = municipio;
//        this.provincia = provincia;
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

