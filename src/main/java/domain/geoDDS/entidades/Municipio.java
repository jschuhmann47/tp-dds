package domain.geoDDS.entidades;

public class Municipio {
    public int id;
    public String nombre;
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
