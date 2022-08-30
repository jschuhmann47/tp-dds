package domain.geoDDS.entidades;

public class Localidad {
    public int idLocalidad;
    public String nombre;
    public int codPostal;
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
