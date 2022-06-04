package domain.geoDDS.entidades;

public class Localidad {
    public int id;
    public String nombre;
    public int codPostal;
    public Municipio municipio;

    public Localidad() {
    }

    public Localidad(int id, String nombre, int codPostal, Municipio municipio) {
        this.id = id;
        this.nombre = nombre;
        this.codPostal = codPostal;
        this.municipio = municipio;
    }

    public int getId() {
        return this.id;
    }
}
