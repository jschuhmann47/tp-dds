package domain.geoDDS;

import domain.geoDDS.entidades.Localidad;
import domain.geoDDS.entidades.Municipio;
import domain.geoDDS.entidades.Provincia;

public class Direccion {
    private Integer altura;
    private String calle;
    private Localidad localidad;
    private Municipio municipio;
    private Provincia provincia; //revisar

    public Direccion(Integer altura, String calle, Localidad localidad, Municipio municipio, Provincia provincia) {
        this.altura = altura;
        this.calle = calle;
        this.localidad = localidad;
        this.municipio = municipio;
        this.provincia = provincia;
    }


    public Municipio getMunicipio() {
        return municipio;
    }


    public Provincia getProvincia() {
        return provincia;
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

